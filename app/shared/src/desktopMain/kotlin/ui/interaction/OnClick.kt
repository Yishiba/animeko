/*
 * Ani
 * Copyright (C) 2022-2024 Him188
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.him188.ani.app.ui.interaction

import androidx.compose.foundation.Indication
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.onClick
import androidx.compose.ui.Modifier

actual fun Modifier.onClickEx(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean,
    onDoubleClick: (() -> Unit)?,
    onLongClick: (() -> Unit)?,
    onClick: () -> Unit
): Modifier {
    return onClick(
        enabled = enabled,
        interactionSource = interactionSource,
        onDoubleClick = onDoubleClick,
        onLongClick = onLongClick,
        onClick = onClick,
    ).indication(interactionSource, indication)
}