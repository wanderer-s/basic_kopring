package com.basic.boards.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kotlin.math.min

@Entity
class Comment(
    id: Long,

    @ManyToOne()
    @JoinColumn(name = "user_id")
    val author: User,

    @ManyToOne()
    @JoinColumn(name = "post_id")
    val post: Post,

    @Column(nullable = false, length = 200)
    var comment: String
): BaseEntity()
