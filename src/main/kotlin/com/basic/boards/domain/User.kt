package com.basic.boards.domain

import com.password4j.Password
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class User(
    @Column(nullable = false, length = 10)
    var nickname: String,
    @Column(nullable = false)
    var password: String
) : BaseEntity() {

    fun passwordCheck(psw: String) = Password.check(psw, password).withBcrypt()
    companion object {
        fun hash(psw: String) = Password.hash(psw).withBcrypt().result
    }
}
