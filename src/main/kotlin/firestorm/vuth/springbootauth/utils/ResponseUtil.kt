package firestorm.vuth.springbootauth.utils

import firestorm.vuth.springbootauth.dto.response.ApiResponse
import org.springframework.http.HttpStatus

object ApiSuccess {
    fun <T> withData(status: HttpStatus = HttpStatus.OK, data: T, message: String = "Success") =
        ApiResponse(
            status = status.value(),
            data = data,
            message = message
        )

    fun message(status: HttpStatus = HttpStatus.OK, message: String) =
        ApiResponse(
            status = status.value(),
            data = null,
            message = message
        )
}

object ApiError {
    fun unauthorized(message: String = "Unauthorized") = ApiResponse<Nothing>(HttpStatus.UNAUTHORIZED.value(), null, message)
    fun notFound(message: String = "Not Found") = ApiResponse<Nothing>(HttpStatus.NOT_FOUND.value(), null, message)
    fun badRequest(message: String = "Bad Request") = ApiResponse<Nothing>(HttpStatus.BAD_REQUEST.value(), null, message)
    fun forbidden(message: String = "Forbidden") = ApiResponse<Nothing>(HttpStatus.FORBIDDEN.value(), null, message)
    fun conflict(message: String = "Conflict") = ApiResponse<Nothing>(HttpStatus.CONFLICT.value(), null, message)
    fun internal(message: String = "Internal Server Error") = ApiResponse<Nothing>(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, message)
}