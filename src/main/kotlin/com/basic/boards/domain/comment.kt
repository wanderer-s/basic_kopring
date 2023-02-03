package com.basic.boards.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Comment(
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private var post: Post,

    @ManyToOne()
    @JoinColumn(name = "user_id")
    val author: User,


    @Column(nullable = false, length = 200)
    var comment: String
): BaseEntity() {
    companion object {
        fun checkLengthComment(comment: String)= comment.length in 5 until 200

    }
}
