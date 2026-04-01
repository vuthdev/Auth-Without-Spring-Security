package firestorm.vuth.springbootauth.dto.response

data class BaseResponse<T>(
    val status: Int,
    val data: T?,
    val message: String
)
