package com.basic.boards.service

import com.basic.boards.domain.Post
import com.basic.boards.domain.User
import com.basic.boards.exceptions.ForbiddenException
import com.basic.boards.repository.PostRepository
import com.basic.boards.repository.UserRepository
import com.basic.boards.request.PostRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull

class PostServiceTest: DescribeSpec({
    val postRepository = mockk<PostRepository>();
    val userRepository = mockk<UserRepository>()
    val postService = PostService(postRepository, userRepository)

    val testUser = User("test", User.hash("password"))
    val testPost = Post("first Title", "first contents", testUser)
    every { postRepository.findByIdOrNull(100L) } answers { null }
    every { postRepository.findByIdOrNull(1L)} answers { testPost }
    every { postRepository.save(testPost)} answers { testPost }
    every { postRepository.deleteById(1L)} answers { Unit }

    describe("get") {
        it("존재하지 않는 ID를 service.findById 호출하면 throw EntityNotFoundException") {

            shouldThrow<EntityNotFoundException> {
                postService.findById(100L)
            }
        }

        it("존재하는 Id로 service.findById 호출시 PostResponse 반환") {
            val result = postService.findById(1L)
            result.id shouldBe 0L
        }

        it("조회 할때마다 viewCount 상승") {
            val foundPost1 = postService.findById(1L)
            val foundPost2 = postService.findById(1L)

            foundPost1.viewCount shouldBe foundPost2.viewCount - 1
        }
    }

    describe("create") {
        it("post의 내용이 너무 길면 throw IllegalArgumentException") {
            shouldThrow<IllegalArgumentException> {
                val postRequest = PostRequest("first title", "first contents".repeat(500))
                postService.createPost(postRequest, User("test", User.hash("1q2w3e4RR!@")))
            }
        }
    }

    describe("edit") {
        it("존재하지 않는 게시물 수정 요청시 throw EntityNotFoundException") {
            shouldThrow<EntityNotFoundException> {
                val postRequest = PostRequest("Edit title", "Edit contents")
                postService.editPost(100L, postRequest)
            }
        }
        it("수정 시 본문 내용이 너무 길거나 짧으면 throw IllegalArgumentException") {
            shouldThrow<IllegalArgumentException> {
                val postRequest = PostRequest("Edit title", "Edit contents".repeat(500))
                postService.editPost(1L, postRequest)
            }
        }
    }

    describe("delete") {
        it("존재하는 Id와 일치하는 password로 service.deletePost 호출 시 Unit") {
            postService.deletePost(1L, "password")
        }

        it("존재하는 Id와 일치하지 않는 password로 service.deletePost 호출 시 throw ForbiddenException") {
            shouldThrow<ForbiddenException> {
                postService.deletePost(1L, "123123")
            }
        }
        it("존재하지 않는 id로 호출 시 throw EntityNotFoundException") {
            shouldThrow<EntityNotFoundException> {
                postService.deletePost(100L, "123123")
            }
        }
    }
})
