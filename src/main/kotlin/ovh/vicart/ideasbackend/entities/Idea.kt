package ovh.vicart.ideasbackend.entities

import javax.persistence.*

@Entity(name = "idea")
class Idea {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(nullable = false)
    lateinit var title: String

    lateinit var text: String
    var favorite: Boolean? = null

    @ManyToOne
    @JoinColumn(name = "id_user")
    lateinit var user: Users

    @OneToMany(mappedBy = "idea")
    lateinit var shared: List<Shares>
}