package ovh.vicart.ideasbackend.entities

import ovh.vicart.ideasbackend.entities.composite.SharesCompositeKey
import javax.persistence.*

@Entity(name = "shares")
class Shares {

    @EmbeddedId
    lateinit var key: SharesCompositeKey

    @ManyToOne
    @MapsId("ownerUser")
    @JoinColumn(name = "id_user_owner")
    lateinit var ownerUsers: Users

    @ManyToOne
    @MapsId("targetUser")
    @JoinColumn(name = "id_user_target")
    lateinit var targetUsers: Users

    @ManyToOne
    @MapsId("idea")
    @JoinColumn(name = "id_idea")
    lateinit var idea: Idea
}