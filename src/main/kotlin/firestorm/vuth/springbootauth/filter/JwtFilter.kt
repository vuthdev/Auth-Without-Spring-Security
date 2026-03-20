package firestorm.vuth.springbootauth.filter

import firestorm.vuth.springbootauth.exception.TokenException
import firestorm.vuth.springbootauth.security.JwtService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtService: JwtService
): OncePerRequestFilter() {

    private val whiteList = listOf("/user/login", "/user/register")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath in whiteList) {
            filterChain.doFilter(request, response)
            return
        }

        val token = request.getHeader("Authorization")

        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "missing token")
            return
        }

        try {
            if (jwtService.isTokenExpired(token.removePrefix("Bearer "))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
                return
            }
        } catch (e: TokenException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
            return
        }

        filterChain.doFilter(request, response)
    }
}