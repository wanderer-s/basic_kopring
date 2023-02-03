package com.basic.boards.service

import com.basic.boards.domain.Comment
import com.basic.boards.domain.Post
import com.basic.boards.domain.User
import com.basic.boards.repository.CommentRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull

class CommentServiceTest: DescribeSpec({
    val commentRepository = mockk<CommentRepository>()

    val commentService = CommentService(commentRepository)

    val testUser = User("nickname", User.hash("1q2w3e4rR!@"))
    val testPost = Post("test", "test contents", testUser)
    val testComment = Comment(testPost, testUser, "test comment")
    every { commentRepository.save(testComment) } answers { testComment }
    every { commentRepository.findByIdOrNull(1L)} answers {testComment}
    every { commentRepository.findByIdOrNull(100L)} answers { null }

    describe("create") {
        it("comment가 너무 짧거나 길시에 throw IllegalArgumentException") {
            shouldThrow<IllegalArgumentException> {
                commentService.createComment(testPost, testUser, "looool".repeat(200))
            }
        }
    }

    describe("edit") {
        it("comment가 너무 짧거나 길시에 throw IllegalArgumentException") {
            shouldThrow<IllegalArgumentException> {
                commentService.editComment(1L,"looool".repeat(200))
            }
        }

        it("존재하지 않는 comment를 수정하려고 요청하면 throw EntityNotFoundException") {
            shouldThrow<EntityNotFoundException> {
                commentService.editComment(100L, "lol")
            }
        }
    }

    describe("delete") {
        it("존재하지 않는 comment를 삭제하려고 요청하면 throw EntityNotFoundException") {
            shouldThrow<EntityNotFoundException> {
                commentService.deleteComment(100L)
            }
        }
    }
})
