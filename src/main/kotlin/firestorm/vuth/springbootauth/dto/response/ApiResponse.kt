package firestorm.vuth.springbootauth.dto.response

data class ApiResponse<T>(
    val status: Int,
    val data: T?,
    val message: String
)
