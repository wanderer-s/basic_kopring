package com.basic.boards.request

data class PostUserRequest(
    val title: String,
    val contents: String,
    val user: UserRequest
)

data class PostRequest(
    val title: String,
    val contents: String
) {
    companion object {
        fun contentsLenghCheck(contents: String) = contents.length in 10 until 1000
    }
}
