package com.basic.boards.exceptions

import com.basic.boards.response.ApiResponse
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


class ForbiddenException(message: String = "접근 권한이 없습니다") : RuntimeException(message)
@RestControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    private val logging = KotlinLogging.logger {}

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFoundException(exception: EntityNotFoundException): ResponseEntity<ApiResponse<Unit>> {
        logging.error { exception }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(exception.message))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequestException(exception: IllegalArgumentException): ResponseEntity<ApiResponse<Unit>> {
        logging.error {exception}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(exception.message))
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handlerForbiddenException(exception: RuntimeException): ResponseEntity<ApiResponse<Unit>> {
        logging.error {exception}
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(exception.message))
    }

}
