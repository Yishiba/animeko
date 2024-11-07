/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.platform.window

import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform
import kotlinx.coroutines.delay
import me.him188.ani.app.platform.PlatformWindow

internal class MacosWindowUtils : AwtWindowUtils() {
    @Suppress("FunctionName")
    interface Quartz : Library {
        // Function to hide the cursor
        fun CGDisplayHideCursor(displayID: Int)

        // Function to show the cursor
        fun CGDisplayShowCursor(displayID: Int)

        // Function to check if the cursor is visible
        fun CGCursorIsVisible(): Boolean

        companion object {
            val INSTANCE: Quartz = Native.load(if (Platform.isMac()) "Quartz" else "c", Quartz::class.java)
        }
    }

    override suspend fun setUndecoratedFullscreen(
        window: PlatformWindow,
        windowState: WindowState,
        undecorated: Boolean
    ) {
//        val awtWindow = (window.windowScope as? FrameWindowScope)?.window ?: return
        if (undecorated) {
            if (windowState.placement == WindowPlacement.Fullscreen) {
                return
            }

//            window.savedMacosWindowState = SavedMacosWindowState(windowState.position, windowState.size)

            // CMP bug, 必须先 Maximized 并且等系统动画完成再 Fullscreen, 否则取消 Fullscreen 时窗口会变为 maximized
            windowState.placement = WindowPlacement.Maximized
            delay(1000) // 留足时间. 窗口越小, 需要的时间越多.
            windowState.placement = WindowPlacement.Fullscreen
        } else {
            if (windowState.placement == WindowPlacement.Floating) {
                return
            }

            windowState.placement = WindowPlacement.Floating
//            awtWindow.extendedState = awtWindow.extendedState and MAXIMIZED_BOTH.inv()
        }
    }

    override fun isCursorVisible(window: ComposeWindow): Boolean {
        return Quartz.INSTANCE.CGCursorIsVisible()
    }

    override fun setCursorVisible(window: ComposeWindow, visible: Boolean) {
        Quartz.INSTANCE.apply {
            if (visible) {
                CGDisplayShowCursor(0)
            } else {
                CGDisplayHideCursor(0)
            }
        }
    }
}