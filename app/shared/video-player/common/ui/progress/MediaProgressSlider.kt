package me.him188.ani.app.videoplayer.ui.progress

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import me.him188.ani.app.ui.foundation.theme.aniDarkColorTheme
import me.him188.ani.app.ui.foundation.theme.looming
import me.him188.ani.app.ui.foundation.theme.weaken
import me.him188.ani.app.videoplayer.ui.state.PlayerState
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import kotlin.math.roundToLong

/**
 * 播放器进度滑块的状态.
 *
 * - 支持从 [currentPositionMillis] 同步当前播放位置, 从 [totalDurationMillis] 同步总时长.
 * - 使用 [onPreview] 和 [onPreviewFinished] 来处理用户拖动进度条的事件.
 * 
 * @see MediaProgressSlider
 */
@Stable
class MediaProgressSliderState(
    currentPositionMillis: () -> Long,
    totalDurationMillis: () -> Long,
    /**
     * 当用户正在拖动进度条时触发. 每有一个 change 都会调用.
     */
    private val onPreview: (positionMillis: Long) -> Unit,
    /**
     * 当用户松开进度条时触发. 此时播放器应当要跳转到该位置.
     */
    private val onPreviewFinished: (positionMillis: Long) -> Unit,
) {
    val currentPositionMillis: Long by derivedStateOf(currentPositionMillis)
    val totalDurationMillis: Long by derivedStateOf(totalDurationMillis)

    private var previewPositionMillis: Long by mutableStateOf(-1L)

    private fun previewPosition(positionMillis: Long) {
        previewPositionMillis = positionMillis
        onPreview(positionMillis)
    }

    /**
     * Sets the slider to move to the given position.
     * [onPreview] will be triggered.
     */
    fun previewPositionRatio(ratio: Float) {
        previewPosition((totalDurationMillis * ratio).roundToLong())
    }

    /**
     * The position to display in the progress slider. If a preview is active, this will be the preview position.
     */
    private val displayPositionMillis: Long by derivedStateOf {
        val previewPositionMillis = previewPositionMillis
        if (previewPositionMillis != -1L) {
            previewPositionMillis
        } else {
            this.currentPositionMillis
        }
    }

    /**
     * The ratio of the current display position to the total duration. Range is `0..1`
     */
    val displayPositionRatio by derivedStateOf {
        val total = this.totalDurationMillis
        if (total == 0L) {
            return@derivedStateOf 0f
        }
        displayPositionMillis.toFloat() / total
    }

    fun finishPreview() {
        onPreviewFinished(previewPositionMillis)
        previewPositionMillis = -1L
    }
}

/**
 * 便捷方法, 从 [PlayerState.currentPositionMillis] 创建  [MediaProgressSliderState]
 */
@Composable
fun rememberMediaProgressSliderState(
    playerState: PlayerState,
    onPreview: (positionMillis: Long) -> Unit,
    onPreviewFinished: (positionMillis: Long) -> Unit,
): MediaProgressSliderState {
    val currentPosition by playerState.currentPositionMillis.collectAsStateWithLifecycle()
    val totalDuration by remember(playerState) {
        playerState.videoProperties.filterNotNull().map { it.durationMillis }.distinctUntilChanged()
    }.collectAsStateWithLifecycle(0L)

    val onPreviewUpdated by rememberUpdatedState(onPreview)
    val onPreviewFinishedUpdated by rememberUpdatedState(onPreviewFinished)
    return remember(currentPosition, totalDuration) {
        MediaProgressSliderState(
            { currentPosition },
            { totalDuration },
            onPreviewUpdated,
            onPreviewFinishedUpdated,
        )
    }
}


/**
 * The slider to control the progress of a video, with a preview feature.
 */
// Preview: PreviewVideoScaffoldFullscreen
@Composable
fun MediaProgressSlider(
    state: MediaProgressSliderState,
    modifier: Modifier = Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        val interactionSource = remember { MutableInteractionSource() }
        MaterialTheme(aniDarkColorTheme()) {
            Slider(
                value = state.displayPositionRatio,
                valueRange = 0f..1f,
                onValueChange = { state.previewPositionRatio(it) },
                interactionSource = interactionSource,
                thumb = {
                    SliderDefaults.Thumb(
                        interactionSource = interactionSource,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                        ),
                        enabled = true,
//                            thumbSize = DpSize(16.dp, 16.dp)
                    )
                },
                track = {
                    SliderDefaults.Track(
                        it,
                        colors = SliderDefaults.colors(
                            inactiveTrackColor = MaterialTheme.colorScheme.background.weaken(),
                            activeTrackColor = MaterialTheme.colorScheme.onBackground.looming(),
                        ),
                    )
                },
                onValueChangeFinished = {
                    state.finishPreview()
                },
                modifier = Modifier.height(24.dp),
            )
        }
    }
}
