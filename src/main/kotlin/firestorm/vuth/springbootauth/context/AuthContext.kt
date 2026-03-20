package firestorm.vuth.springbootauth.context

import firestorm.vuth.springbootauth.exception.NotFoundException
import firestorm.vuth.springbootauth.exception.UnauthorizedException
import firestorm.vuth.springbootauth.model.User
import firestorm.vuth.springbootauth.repository.UserRepository
import firestorm.vuth.springbootauth.security.JwtService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class AuthContext(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val request: HttpServletRequest
) {
    private fun getToken(): String {
        return request.getHeader("Authorization")
            ?.removePrefix("Bearer ")
            ?: throw UnauthorizedException("missing token")
    }

    fun getCurrentUser(): User {
        val username = jwtService.getUsernameFromToken(getToken())
        return userRepository.findByUsername(username)
            ?: throw NotFoundException("User $username not found")
    }

    fun isAuthenticated(): Boolean {
        val username = jwtService.getUsernameFromToken(getToken())
        return userRepository.findByUsername(username) != null
    }
}