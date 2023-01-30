package com.basic.boards.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class User(
    id: Long,
    @Column(nullable = false, length = 10)
    var nickname: String,
    @Column(nullable = false)
    private var password: String
) : BaseEntity()
