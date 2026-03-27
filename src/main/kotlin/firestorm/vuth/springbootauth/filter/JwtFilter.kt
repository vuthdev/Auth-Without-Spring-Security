package firestorm.vuth.springbootauth.filter

import firestorm.vuth.springbootauth.exception.TokenException
import firestorm.vuth.springbootauth.security.JwtService
import firestorm.vuth.springbootauth.utils.ApiError
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import tools.jackson.databind.ObjectMapper

@Component
class JwtFilter(
    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper
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
            jwtService.parseClaims(token.removePrefix("Bearer "))
        } catch (e: TokenException) {
            writeError(response, e.message ?: "Invalid token")
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun writeError(response: HttpServletResponse, message: String) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        objectMapper.writeValue(response.writer, ApiError.unauthorized(message))
    }
}