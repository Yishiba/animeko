/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.subject.episode.comments

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.him188.ani.app.ui.comment.CommentEditorState
import me.him188.ani.app.ui.comment.CommentMapperContext
import me.him188.ani.app.ui.comment.EditComment
import me.him188.ani.app.ui.comment.EditCommentBottomStubPanel
import me.him188.ani.app.ui.comment.EditCommentDefaults
import me.him188.ani.app.ui.comment.EditCommentSticker
import me.him188.ani.app.ui.comment.createPreviewTurnstileState
import me.him188.ani.app.ui.foundation.ProvideFoundationCompositionLocalsForPreview
import me.him188.ani.app.ui.foundation.rememberBackgroundScope

@Preview
@Composable
fun PreviewEditComment() {
    ProvideFoundationCompositionLocalsForPreview {
        val scope = rememberBackgroundScope()
        EditComment(
            state = remember {
                CommentEditorState(
                    showExpandEditCommentButton = true,
                    initialEditExpanded = false,
                    panelTitle = mutableStateOf("评论：我心里危险的东西 第二季"),
                    stickers = mutableStateOf(
                        (0..64)
                            .map { EditCommentSticker(it, null) }
                            .toList(),
                    ),
                    onSend = { _, _ -> true },
                    richTextRenderer = {
                        withContext(Dispatchers.Default) {
                            with(CommentMapperContext) { parseBBCode(it) }
                        }
                    },
                    backgroundScope = scope.backgroundScope,
                )
            },
            turnstileState = remember { createPreviewTurnstileState() },
        )
    }
}

@Preview
@Composable
fun PreviewEditCommentStickerPanel() {
    ProvideFoundationCompositionLocalsForPreview {
        EditCommentDefaults.StickerSelector(
            list = (0..64)
                .map { EditCommentSticker(it, null) }
                .toList(),
            onClickItem = { },
        )
    }
}

@Preview
@Composable
fun PreviewEditCommentBottomStubPanel() {
    ProvideFoundationCompositionLocalsForPreview {
        EditCommentBottomStubPanel(
            text = TextFieldValue("发送评论"),
            onClickEditText = { },
            onClickEmoji = { },
        )
    }
}