package ovh.vicart.ideasbackend.entities.composite

import ovh.vicart.ideasbackend.entities.Users
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class SharesCompositeKey : Serializable {

    @Column(name = "id_user_owner")
    var ownerUser: Long? = null
    @Column(name = "id_user_target")
    var targetUser: Long? = null
    @Column(name = "id_idea")
    var idea: Long? = null
}