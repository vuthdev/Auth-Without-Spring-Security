package firestorm.vuth.springbootauth.utils

import firestorm.vuth.springbootauth.dto.response.BaseResponse
import org.springframework.http.HttpStatus

object ApiSuccess {
    fun <T> withData(status: HttpStatus = HttpStatus.OK, data: T, message: String = "Success") =
        BaseResponse(
            status = status.value(),
            data = data,
            message = message
        )

    fun message(status: HttpStatus = HttpStatus.OK, message: String) =
        BaseResponse(
            status = status.value(),
            data = null,
            message = message
        )
}

object ApiError {
    fun unauthorized(message: String = "Unauthorized") = BaseResponse(HttpStatus.UNAUTHORIZED.value(), null, message)
    fun notFound(message: String = "Not Found") = BaseResponse(HttpStatus.NOT_FOUND.value(), null, message)
    fun badRequest(message: String = "Bad Request") = BaseResponse(HttpStatus.BAD_REQUEST.value(), null, message)
    fun forbidden(message: String = "Forbidden") = BaseResponse(HttpStatus.FORBIDDEN.value(), null, message)
    fun conflict(message: String = "Conflict") = BaseResponse(HttpStatus.CONFLICT.value(), null, message)
    fun internal(message: String = "Internal Server Error") = BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, message)
}