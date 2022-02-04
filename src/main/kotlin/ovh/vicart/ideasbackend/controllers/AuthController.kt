package ovh.vicart.ideasbackend.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ovh.vicart.ideasbackend.components.JwtTokenUtil
import ovh.vicart.ideasbackend.entities.Users
import ovh.vicart.ideasbackend.models.CredentialsAuth
import ovh.vicart.ideasbackend.models.RegisterCredentialsAuth
import ovh.vicart.ideasbackend.models.TokenizedAuth
import ovh.vicart.ideasbackend.repositories.UserRepository
import ovh.vicart.ideasbackend.services.JwtUserService

@RestController
@CrossOrigin
class AuthController {

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var jwtUserService: JwtUserService

    @Autowired
    private lateinit var userRepo: UserRepository

    @PostMapping("/auth")
    fun postAuth(@RequestBody auth: CredentialsAuth) : ResponseEntity<Any> {
        val matcher = ExampleMatcher.matching().withIgnorePaths("picturePath")
            .withIgnorePaths("email")
            .withIgnorePaths("displayName")
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
            val user = jwtUserService.loadByUsername(username)
            return ResponseEntity.ok(user)
        }
        return ResponseEntity.badRequest().build()
    }

    @PostMapping("/register")
    fun postRegister(@RequestBody cred: RegisterCredentialsAuth) : ResponseEntity<Any> {
        val user = Users()
        user.username = cred.username
        user.displayName = cred.displayName
        user.email = cred.email
        user.password = cred.password
        userRepo.save(user)
        return ResponseEntity.ok().build()
    }
}