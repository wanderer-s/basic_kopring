package com.basic.boards.request

data class UserRequest(
    val nickname: String,
    val password: String,
    val passwordCheck: String
) {
    fun arePasswordsSame() = password == passwordCheck
    fun nicknameLengthCheck() = nickname.length in 2..9
}

data class Password(
    val password: String
) {
    companion object {
        fun check(password: String): Boolean {
            val regexCondition = Regex("""(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#${'$'}%^&*])[\da-zA-Z!@#${'$'}%^&*]{6,20}""")
            return password.matches(regexCondition)
        }
    }
}
