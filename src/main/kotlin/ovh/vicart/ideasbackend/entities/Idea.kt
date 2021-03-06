package ovh.vicart.ideasbackend.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.OffsetDateTime
import javax.persistence.*

@Entity(name = "idea")
class Idea {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(nullable = false)
    lateinit var title: String

    lateinit var text: String

    var datetime: OffsetDateTime = OffsetDateTime.now()
    var favorite: Boolean? = null

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference
    var user: Users? = null

    @OneToMany(mappedBy = "idea")
    var shared: List<Shares>? = null
}