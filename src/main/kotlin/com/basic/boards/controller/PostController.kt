package com.basic.boards.controller

import com.basic.boards.request.Password
import com.basic.boards.request.PostRequest
import com.basic.boards.request.PostUserRequest
import com.basic.boards.response.Pagination
import com.basic.boards.response.PostListResponse
import com.basic.boards.response.PostResponse
import com.basic.boards.service.PostService
import com.basic.boards.service.UserService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService,
    private val userService: UserService
) {
    @GetMapping
    fun findAll(
        @ModelAttribute pagination: Pagination): Page<PostListResponse> {
        return postService.findAll(pagination)
    }

    @GetMapping("/{postId}")
    fun findBoard(@PathVariable postId: Long): PostResponse {
        return postService.findById(postId)
    }

    @PostMapping
    fun createPost(@RequestBody postUserRequest: PostUserRequest): PostResponse {
        with (postUserRequest) {
            val newUser = userService.createUser(user)
            return postService.createPost(PostRequest(title,contents), newUser)
        }
    }

    @PutMapping("/{postId}")
    fun modifyPost(@PathVariable postId: Long, @RequestBody postRequest: PostRequest): PostResponse {
        return postService.editPost(postId, postRequest)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long, @RequestBody password: Password) {
        postService.deletePost(postId, password.password)
    }

    @PostMapping("/{postId}/auth")
    fun authUser(@PathVariable postId: Long, @RequestBody password: Password) {
        val foundPost = postService.get(postId)
        userService.authUser(password.password, foundPost.author!!)
    }
}
