package firestorm.vuth.springbootauth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthenticatedException(e: UnauthorizedException) = mapOf("error" to e.message)

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(e: NotFoundException) = mapOf("error" to e.message)

    @ExceptionHandler(TokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleTokenException(e: TokenException) = mapOf("error" to e.message)

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenException(e: ForbiddenException) = mapOf("error" to e.message)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handOtherException(e: Exception) = mapOf("error" to e.message)
}