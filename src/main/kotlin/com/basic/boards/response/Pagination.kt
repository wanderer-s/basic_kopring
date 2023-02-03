package com.basic.boards.response

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class Pagination(
    val page: Int = 1,
    val size: Int = 20
) {
    fun pageRequest() = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"))
}
