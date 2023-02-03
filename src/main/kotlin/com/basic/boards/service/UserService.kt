package com.basic.boards.service

import com.basic.boards.domain.User
import com.basic.boards.exceptions.ForbiddenException
import com.basic.boards.repository.UserRepository
import com.basic.boards.request.Password
import com.basic.boards.request.UserRequest
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(userRequest: UserRequest): User {
        with(userRequest) {
            if(!nicknameLengthCheck()) throw IllegalArgumentException("nickname의 길이는 2글자에서 10글자 사이만 가능합니다")
            if(!Password.check(password)) throw IllegalArgumentException("비밀번호는 대소문자와 숫자, 특수문자가 최소 1개는 포함되어 있어야 하며 20글자를 넘을 수 없습니다")
            if (!arePasswordsSame()) throw IllegalArgumentException("비밀번호와 비밀번호 확인이 다릅니다")
            val hashedPassword = User.hash(password)
            val newUser = User(nickname, hashedPassword )
            return userRepository.save(newUser)
        }
    }

    fun authUser(password: String, user: User) {
        if (!user.passwordCheck(password)) throw ForbiddenException()
    }
}
