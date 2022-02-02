package ovh.vicart.ideasbackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ovh.vicart.ideasbackend.entities.Idea

interface IdeaRepository : JpaRepository<Idea, Long> {
}