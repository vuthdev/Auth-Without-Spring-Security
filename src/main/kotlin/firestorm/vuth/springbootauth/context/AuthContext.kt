package firestorm.vuth.springbootauth.context

import firestorm.vuth.springbootauth.exception.UnauthorizedException
import firestorm.vuth.springbootauth.filter.JwtFilter
import firestorm.vuth.springbootauth.model.User
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class AuthContext(
    private val request: HttpServletRequest
) {
    fun getCurrentUser(): User =
        (request.getAttribute(JwtFilter.CURRENT_USER)
            ?: throw UnauthorizedException("missing token")) as User

    fun isAuthenticated(): Boolean =
        (request.getAttribute(JwtFilter.CURRENT_USER) != null)
}