package firestorm.vuth.springbootauth.exception

import firestorm.vuth.springbootauth.dto.response.BaseResponse
import firestorm.vuth.springbootauth.common.util.ApiError
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthenticatedException(e: UnauthorizedException): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiError.unauthorized(e.message ?: "Authentication failed")
        )

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiError.notFound(e.message ?: "Not Found")
        )

    @ExceptionHandler(TokenException::class)
    fun handleTokenException(e: TokenException): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiError.unauthorized(e.message ?: "Unauthorized")
        )

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(e: ForbiddenException): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ApiError.forbidden(e.message ?: "Forbidden")
        )

    @ExceptionHandler(AlreadyExistsException::class)
    fun handleAlreadyExistsException(e: AlreadyExistsException): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiError.conflict(e.message ?: "Conflict")
        )

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleTokenExpiredException(e: ExpiredJwtException): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiError.unauthorized(e.message ?: "Token expired")
        )

    @ExceptionHandler(JwtException::class)
    fun handleJwtException(e: JwtException): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiError.unauthorized(e.message ?: "Unauthorized")
        )

    @ExceptionHandler(Exception::class)
    fun handOtherException(e: Exception): ResponseEntity<BaseResponse<Nothing>> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiError.internal(e.message ?: "An unexpected error occurred")
        )
}