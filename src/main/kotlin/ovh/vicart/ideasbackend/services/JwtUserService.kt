package ovh.vicart.ideasbackend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import ovh.vicart.ideasbackend.entities.Users
import ovh.vicart.ideasbackend.repositories.UserRepository

@Service
class JwtUserService {

    @Autowired
    private lateinit var userRepo: UserRepository

    fun loadByUsername(username: String) : Users {
        return userRepo.findOne(Example.of(Users().apply { this.username = username }, ExampleMatcher.matching()
            .withIgnoreNullValues())).get()
    }
}