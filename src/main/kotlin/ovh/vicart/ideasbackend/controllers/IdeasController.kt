package ovh.vicart.ideasbackend.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ovh.vicart.ideasbackend.components.JwtTokenUtil
import ovh.vicart.ideasbackend.entities.Idea
import ovh.vicart.ideasbackend.repositories.IdeaRepository
import ovh.vicart.ideasbackend.services.JwtUserService
import java.util.*

@RestController
class IdeasController {

    @Autowired
    private lateinit var jwtUserService: JwtUserService

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var ideaRepo: IdeaRepository

    @GetMapping("/ideas")
    fun getIdeas(@RequestHeader("authorization") token: String) : ResponseEntity<List<Idea>> {
        val username = jwtTokenUtil.getUsernameFromToken(token)
        if(jwtTokenUtil.validateToken(token, username)) {
            val user = jwtUserService.loadByUsername(username)
            return ResponseEntity.ok(user.ideas)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/ideas/shared")
    fun getSharedIdeas(@RequestHeader("authorization") token: String) : ResponseEntity<List<Idea>> {
        val username = jwtTokenUtil.getUsernameFromToken(token)
        if(jwtTokenUtil.validateToken(token, username)) {
            val user = jwtUserService.loadByUsername(username)
            return ResponseEntity.ok(user.sharesFrom.map { it.idea })
        }
        return ResponseEntity.badRequest().build()
    }

    @PostMapping("/idea")
    fun postIdea(@RequestHeader("authorization") token: String, @RequestBody idea: Idea) : ResponseEntity<Any> {
        val username = jwtTokenUtil.getUsernameFromToken(token)
        if(jwtTokenUtil.validateToken(token, username)) {
            val user = jwtUserService.loadByUsername(username)
            idea.user = user
            ideaRepo.save(idea)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.badRequest().build()
    }

    @PutMapping("/idea/{id}")
    fun putIdea(@RequestHeader("authorization") token: String, @PathVariable id: Long,
        @RequestBody idea: Idea) : ResponseEntity<Any> {
        val username = jwtTokenUtil.getUsernameFromToken(token)
        if(jwtTokenUtil.validateToken(token, username)) {
            val user = jwtUserService.loadByUsername(username)
            val userIdea = user.ideas.any { it.id == id }
            if(userIdea) {
                idea.id = id
                ideaRepo.save(idea)
                return ResponseEntity.ok().build()
            }
        }
        return ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/idea/{id}")
    fun deleteIdea(@RequestHeader("authorization") token: String, @PathVariable id: Long) : ResponseEntity<Any> {
        val username = jwtTokenUtil.getUsernameFromToken(token)
        if(jwtTokenUtil.validateToken(token, username)) {
            val user = jwtUserService.loadByUsername(username)
            val belongsToUser = user.ideas.any { it.id == id }
            if(belongsToUser) {
                ideaRepo.deleteById(id)
                return ResponseEntity.ok().build()
            }
        }
        return ResponseEntity.badRequest().build()
    }
}