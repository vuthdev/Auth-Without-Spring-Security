package firestorm.vuth.springbootauth.security

import firestorm.vuth.springbootauth.exception.TokenException
import firestorm.vuth.springbootauth.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtService {
    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.access-token-expiration}")
    private val accessTokenExpire: Long = 0

    @Value("\${jwt.refresh-token-expiration}")
    private val refreshTokenExpire: Long = 0

    fun generateAccessToken(user: User): String {
        val claims = mapOf(
            "username" to user.username,
            "role" to user.role?.roleName,
            "type" to "access"
        )

        return generateAccessToken(claims, user)
    }

    fun generateAccessToken(extraClaims: Map<String, Any?>, user: User): String {
        return buildToken(user, extraClaims, accessTokenExpire)
    }

    fun generateRefreshToken(user: User): String {
        val claims = mapOf(
            "username" to user.username,
            "type" to "refresh"
        )
        return buildToken(user, claims, accessTokenExpire)
    }

    fun buildToken(user: User, extraClaims: Map<String, Any?>, expiration: Long): String {
        return Jwts.builder()
            .subject(user.id.toString())
            .claims(extraClaims)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignKey())
            .compact()
    }

    fun getUserIdFromToken(token: String): UUID {
        return UUID.fromString(extractClaim(token) { it.subject })
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    fun isValidRefreshToken(token: String): Boolean {
        val claims = parseClaims(token)
        return claims["type"] == "refresh" && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = parseClaims(token)
        return claimsResolver(claims)
    }

    private fun parseClaims(token: String): Claims =
        try {
            Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            throw TokenException("Token expired")
        } catch (e: JwtException) {
            throw TokenException("Token is invalid")
        }

    private fun getSignKey(): SecretKey {
        val keyBytes = Base64.getDecoder().decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}

