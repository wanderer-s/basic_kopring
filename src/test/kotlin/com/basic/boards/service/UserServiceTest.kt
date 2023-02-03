package com.basic.boards.service

import com.basic.boards.domain.User
import com.basic.boards.exceptions.ForbiddenException
import com.basic.boards.repository.UserRepository
import com.basic.boards.request.UserRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk

class UserServiceTest: DescribeSpec({
    val userRepository = mockk<UserRepository>()

    val userService = UserService(userRepository)

    val testUser = User("test", User.hash("password"))

    describe("createUser") {
        it("사용자 이름이 길면 throw IllegalArgumentException") {
            shouldThrow<IllegalArgumentException> {
                val newUser = UserRequest("test".repeat(15), "1q2w3e4RR!@", "1q2w3e4RR!@")
                userService.createUser(newUser)
            }
        }

        it("사용자 비밀번호 요건을 충족하지 못하면 throw IllegalArgumentException") {
            shouldThrow<IllegalArgumentException> {
                val newUser = UserRequest("test", "123123", "123123")
                userService.createUser(newUser)
            }
        }

        it("사용자 비밀번호와 비밀번호확인이 서로 다르면 throw IllegalArgumentException") {
            shouldThrow<IllegalArgumentException> {
                val newUser = UserRequest("test", "1q2w3e4RR!###", "1q2w3e4RR!@")
                userService.createUser(newUser)
            }
        }
    }

    describe("auth") {
        it("작성자와 다른 비밀번호 입력시 throw ForbiddenException") {
            shouldThrow<ForbiddenException> {
                userService.authUser("1q2w3e4r", testUser)
            }
        }
    }
})
