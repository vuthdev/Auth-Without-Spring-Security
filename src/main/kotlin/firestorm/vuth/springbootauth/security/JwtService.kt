package firestorm.vuth.springbootauth.security

import firestorm.vuth.springbootauth.exception.NotFoundException
import firestorm.vuth.springbootauth.exception.TokenException
import firestorm.vuth.springbootauth.model.Role
import firestorm.vuth.springbootauth.model.User
import firestorm.vuth.springbootauth.repository.RoleRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Base64
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String,
) {
    private val log = LoggerFactory.getLogger(JwtService::class.java)

    fun generateToken(user: User): String {
        log.debug("Generating token for user ${user.username} with role ${user.role?.roleName}")

        return Jwts.builder()
            .subject(user.id.toString())
            .claim("username", user.username)
            .claim("role", user.role?.roleName)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 600000))
            .signWith(getSignKey())
            .compact()
    }

    fun getUsernameFromToken(token: String): String? {
        return parseClaims(token).get("username", String::class.java)
    }

    fun isTokenExpired(token: String): Boolean {
        val claims = parseClaims(token)
        return claims.expiration.before(Date())
    }

    fun parseClaims(token: String): Claims =
        try {
            Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            throw TokenException("Token has expired.")
        } catch (e: JwtException) {
            throw TokenException("Token is invalid.")
        }

    fun getSignKey(): SecretKey {
        val keyBytes = Base64.getDecoder().decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
