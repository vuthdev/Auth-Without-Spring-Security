package firestorm.vuth.springbootauth.security

import firestorm.vuth.springbootauth.exception.TokenException
import firestorm.vuth.springbootauth.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Base64
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value($$"${jwt.secret}")
    private val secret: String
) {
    fun generateAccessToken(user: User): String {

        return Jwts.builder()
            .subject(user.id.toString())
            .claim("username", user.username)
            .claim("role", user.role?.roleName)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 600000))
            .signWith(getSignKey())
            .compact()
    }

    fun getUserIdFromToken(token: String): UUID {
        return UUID.fromString(parseClaims(token).subject)
    }

    fun parseClaims(token: String): Claims =
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

    fun getSignKey(): SecretKey {
        val keyBytes = Base64.getDecoder().decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
