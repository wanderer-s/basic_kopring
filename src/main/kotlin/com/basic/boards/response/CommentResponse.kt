package com.basic.boards.response

import com.basic.boards.domain.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val authorName: String,
    val comment: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        operator fun invoke(comment: Comment) =
            CommentResponse(
                comment.id,
                comment.author.nickname,
                comment.comment,
                comment.createdAt,
                comment.updatedAt
            )
    }
}
