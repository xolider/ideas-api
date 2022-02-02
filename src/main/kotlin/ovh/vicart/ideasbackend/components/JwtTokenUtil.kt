package ovh.vicart.ideasbackend.components

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ovh.vicart.ideasbackend.entities.Users
import java.io.Serializable
import java.time.Instant
import java.util.*
import java.util.function.Function
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Component
class JwtTokenUtil : Serializable {

    companion object {
        val JWT_TOKEN_VALIDITY: Long = 5*60*60
    }

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun getUsernameFromToken(token: String) : String {
        return getClaimFromToken(token, Claims::getSubject)
    }

    fun getExpirationDateFromToken(token: String) : Date {
        return getClaimFromToken(token, Claims::getExpiration)
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>) : T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String) : Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String) : Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date.from(Instant.now()))
    }

    fun generateToken(user: Users) : String {
        return doGenerateToken(user.username)
    }

    private fun doGenerateToken(subject: String) : String {
        return Jwts.builder().setSubject(subject).setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusMillis(JWT_TOKEN_VALIDITY*1000)))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    fun validateToken(token: String, usernameProvided: String) : Boolean {
        val username = getUsernameFromToken(token)
        return usernameProvided == username && !isTokenExpired(token)
    }
}