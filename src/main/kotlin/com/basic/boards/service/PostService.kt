package com.basic.boards.service

import com.basic.boards.domain.Post
import com.basic.boards.domain.User
import com.basic.boards.exceptions.ForbiddenException
import com.basic.boards.response.PostResponse
import com.basic.boards.repository.PostRepository
import com.basic.boards.repository.UserRepository
import com.basic.boards.request.Password
import com.basic.boards.request.PostRequest
import com.basic.boards.request.PostUserRequest
import com.basic.boards.response.Pagination
import com.basic.boards.response.PostListResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    fun findAll(pagination: Pagination): Page<PostListResponse> {
        val pageRequest = pagination.pageRequest()
        val postEntityPage =  postRepository.findAll(pageRequest)

        return postEntityPage.map { PostListResponse(it) }
    }

    @Transactional(readOnly = true)
    fun get(id: Long) = postRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("값이 없습니다")


    fun findById(id: Long): PostResponse {
        val post = get(id)
        post.increaseViewCount()
        return PostResponse(post)
    }

    fun createPost(postRequest: PostRequest, user: User): PostResponse {
        with(postRequest) {
            if(!PostRequest.contentsLenghCheck(contents)) throw IllegalArgumentException("본문 내용이 너무 길거나 짧습니다")

            val newPost = Post(title, contents, user)
            postRepository.save(newPost)

            return PostResponse(newPost)
        }
    }

    fun editPost(id: Long, postRequest: PostRequest): PostResponse {
        with(postRequest) {
            if (!PostRequest.contentsLenghCheck(contents)) throw IllegalArgumentException("본문 내용이 너무 길거나 짧습니다")

            val foundPost = get(id)
            foundPost.title = title
            foundPost.contents = contents
            return PostResponse(foundPost)
        }
    }

    fun deletePost(id: Long, password: String): Unit {
        val foundPost = get(id)
        if(!foundPost.author!!.passwordCheck(password)) throw ForbiddenException()
        postRepository.deleteById(id)
    }
}


