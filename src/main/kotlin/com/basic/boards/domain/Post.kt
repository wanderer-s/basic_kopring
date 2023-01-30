package com.basic.boards.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Board(
    id: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, length = 1000)
    var contents: String,

    @ManyToOne()
    val Author: User,

    @Column(nullable = false)
    var viewCount: Int = 0

): BaseEntity(id)
