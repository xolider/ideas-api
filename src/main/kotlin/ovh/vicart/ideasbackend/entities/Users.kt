package ovh.vicart.ideasbackend.entities

import javax.persistence.*

@Entity(name = "users")
class Users {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(unique = true, nullable = false)
    lateinit var username: String
    @Column(unique = true, nullable = false)
    lateinit var email: String
    @Column(nullable = false)
    lateinit var password: String
    @Column(name = "picture_path")
    lateinit var picturePath: String

    @OneToMany(mappedBy = "user")
    lateinit var ideas: List<Idea>

    @OneToMany(mappedBy = "ownerUsers")
    lateinit var sharesTo: List<Shares>

    @OneToMany(mappedBy = "targetUsers")
    lateinit var sharesFrom: List<Shares>
}