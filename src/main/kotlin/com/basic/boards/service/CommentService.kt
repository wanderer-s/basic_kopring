package com.basic.boards.service

import com.basic.boards.domain.Comment
import com.basic.boards.domain.Post
import com.basic.boards.domain.User
import com.basic.boards.exceptions.ForbiddenException
import com.basic.boards.repository.CommentRepository
import com.basic.boards.request.CommentRequest
import com.basic.boards.response.CommentResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
) {

    @Transactional(readOnly = true)
    fun get(id: Long) = commentRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("요청하신 data를 찾을 수 없습니다")

    fun createComment(post: Post, user: User, comment: String): Long {
        if ( !Comment.checkLengthComment(comment)) throw IllegalArgumentException("글의 길이가 너무 짧거나 깁니다")
        val newComment = Comment(post, user, comment)
        commentRepository.save(newComment)
        return newComment.id
        }

    fun editComment(id: Long, comment: String): Long {
        val foundComment = get(id)
        if ( !Comment.checkLengthComment(comment)) throw IllegalArgumentException("글의 길이가 너무 짧거나 깁니다")
        foundComment.comment = comment
        return foundComment.id
    }

    fun deleteComment(id: Long): Unit {
        get(id)
        commentRepository.deleteById(id)
    }
}
