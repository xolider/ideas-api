package ovh.vicart.ideasbackend.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ovh.vicart.ideasbackend.components.JwtTokenUtil
import ovh.vicart.ideasbackend.entities.Users
import ovh.vicart.ideasbackend.models.CredentialsAuth
import ovh.vicart.ideasbackend.models.TokenizedAuth
import ovh.vicart.ideasbackend.repositories.UserRepository

@RestController
class AuthController {

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userRepo: UserRepository

    @PostMapping("/auth")
    fun postAuth(@RequestBody auth: CredentialsAuth) : ResponseEntity<Any> {
        val matcher = ExampleMatcher.matching().withIgnorePaths("picturePath")
            .withIgnorePaths("email")
            .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
        val user = userRepo.findOne(Example.of(Users().apply {
            this.username = auth.username
            this.password = auth.password
        }, matcher)).orElse(null)
        if(user != null) {
            val token = jwtTokenUtil.generateToken(user)
            return ResponseEntity.ok(TokenizedAuth(token))
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/check")
    fun getCheck(@RequestHeader("authorization") token: String) : ResponseEntity<Any> {
        val username = jwtTokenUtil.getUsernameFromToken(token)
        if(jwtTokenUtil.validateToken(token, username)) {
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.badRequest().build()
    }
}