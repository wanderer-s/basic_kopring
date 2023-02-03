package com.basic.boards.response

import com.basic.boards.domain.Comment
import com.basic.boards.domain.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val contents: String,
    val authorName: String,
    val comments: List<CommentResponse?>,
    val viewCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {

    companion object {
        operator fun invoke(post: Post) =
            with(post) {
                PostResponse(
                    id,
                    title,
                    contents,
                    author!!.nickname,
                    comments.map { it?.let { comment -> CommentResponse(comment) } }.sortedByDescending { it?.createdAt },
                    viewCount,
                    createdAt,
                    updatedAt
                )
            }
    }
}

data class PostListResponse(
    val id: Long,
    val title: String,
    val contents: String,
    val authorName: String,
    val commentsCount: Int,
    val viewCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        operator fun invoke(post: Post) =
            with(post) {
                PostListResponse(
                    id,
                    title,
                    contents,
                    author!!.nickname,
                    comments.count(),
                    viewCount,
                    createdAt,
                    updatedAt
                )
            }
    }
}
