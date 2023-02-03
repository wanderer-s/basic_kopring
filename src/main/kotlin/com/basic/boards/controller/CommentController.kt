package com.basic.boards.controller

import com.basic.boards.request.CommentEditRequest
import com.basic.boards.request.CommentRequest
import com.basic.boards.request.Password
import com.basic.boards.service.CommentService
import com.basic.boards.service.PostService
import com.basic.boards.service.UserService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService,
    private val postService: PostService,
    private val userService: UserService
) {
    private val logging = KotlinLogging.logger {}

    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentRequest: CommentRequest
    ): Long {
        val postEntity = postService.get(postId)
        val newUser = userService.createUser(commentRequest.user)
        return commentService.createComment(postEntity, newUser, commentRequest.comment)
    }

    @PutMapping("/{commentId}")
    fun editComment(
        @PathVariable commentId: Long,
        @RequestBody commentEditRequest: CommentEditRequest
    ): Long {
        with(commentEditRequest) {
            return commentService.editComment(commentId, comment)
        }
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ) {
        commentService.deleteComment(commentId)
    }

    @PostMapping("/{commentId}/auth")
    fun authUser(@PathVariable commentId: Long, @RequestBody password: Password) {
        val foundComment = commentService.get(commentId)
        userService.authUser(password.password, foundComment.author)
    }
}
