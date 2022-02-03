package ovh.vicart.ideasbackend.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import ovh.vicart.ideasbackend.entities.Users
import ovh.vicart.ideasbackend.repositories.UserRepository

@RestController
@CrossOrigin
class UsersController {

    @Autowired
    private lateinit var usersRepo: UserRepository
}