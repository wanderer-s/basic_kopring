package com.basic.boards.request

data class CommentRequest(
    val comment: String,
    val user: UserRequest
)

data class CommentEditRequest(
    val comment: String
)
