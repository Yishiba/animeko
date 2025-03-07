/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

@file:OptIn(TestOnly::class)

package me.him188.ani.app.ui.subject.episode.details

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.him188.ani.app.ui.foundation.ProvideCompositionLocalsForPreview
import me.him188.ani.app.ui.subject.episode.mediaFetch.TestMediaSourceResultListPresentation
import me.him188.ani.app.ui.subject.episode.mediaFetch.rememberTestMediaSelectorState
import me.him188.ani.utils.platform.annotations.TestOnly

@Preview(name = "progress = null")
@Composable
private fun PreviewEpisodePlayMediaSelectorSheet() = ProvideCompositionLocalsForPreview {
    EpisodePlayMediaSelector(
        rememberTestMediaSelectorState(),
        { TestMediaSourceResultListPresentation },
        onDismissRequest = {},
        onRefresh = {},
        onRestartSource = {},
        Modifier.background(MaterialTheme.colorScheme.surface),
    )
}

@Preview(name = "progress = 0.7f")
@Composable
private fun PreviewEpisodePlayMediaSelectorSheet2() = ProvideCompositionLocalsForPreview {
    EpisodePlayMediaSelector(
        rememberTestMediaSelectorState(),
        { TestMediaSourceResultListPresentation },
        onDismissRequest = {},
        onRefresh = {},
        onRestartSource = {},
        Modifier.background(MaterialTheme.colorScheme.surface),
    )
}

@Preview(name = "progress = 1f")
@Composable
private fun PreviewEpisodePlayMediaSelectorSheet3() = ProvideCompositionLocalsForPreview {
    EpisodePlayMediaSelector(
        rememberTestMediaSelectorState(),
        { TestMediaSourceResultListPresentation },
        onDismissRequest = {},
        onRefresh = {},
        onRestartSource = {},
        Modifier.background(MaterialTheme.colorScheme.surface),
    )
}
