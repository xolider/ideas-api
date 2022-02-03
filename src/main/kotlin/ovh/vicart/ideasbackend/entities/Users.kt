package ovh.vicart.ideasbackend.entities

import javax.persistence.*

@Entity(name = "users")
class Users {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(unique = true, nullable = false)
    var username: String? = null
    @Column(unique = true, nullable = false)
    var email: String? = null
    @Column(nullable = false)
    var password: String? = null
    @Column(name = "picture_path")
    var picturePath: String? = null

    @OneToMany(mappedBy = "user")
    var ideas: List<Idea>? = null

    @OneToMany(mappedBy = "ownerUsers")
    var sharesTo: List<Shares>? = null

    @OneToMany(mappedBy = "targetUsers")
    var sharesFrom: List<Shares>? = null

    @Column(name = "display_name")
    var displayName: String? = null
}