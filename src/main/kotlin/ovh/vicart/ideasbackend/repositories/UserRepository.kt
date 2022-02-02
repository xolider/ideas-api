package ovh.vicart.ideasbackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ovh.vicart.ideasbackend.entities.Users

interface UserRepository : JpaRepository<Users, Long> {
}