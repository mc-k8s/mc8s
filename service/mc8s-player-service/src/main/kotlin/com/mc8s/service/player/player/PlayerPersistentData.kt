package com.mc8s.service.player.player

import com.mc8s.service.player.api.PlayerModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.Optional
import java.util.UUID

@Entity
class PlayerPersistentData(
        @Id
        var uuid: UUID,

//    var proxyId: UUID?,
//    var serverId: UUID?,
) {
    companion object {
        fun from(playerModel: PlayerModel) = PlayerPersistentData(
                playerModel.getUuid()
        )
    }


    fun patch(patch: Patch) {
//        if (patch.onlineState.isPresent) this.onlineState = patch.onlineState.get()
//        if (patch.proxyId.isPresent) this.proxyId = patch.proxyId.get();
//        if (patch.serverId.isPresent) this.serverId = patch.serverId.get();
    }

    class Patch {
        lateinit var uuid: UUID;

        var onlineState: Optional<Boolean> = Optional.empty()
        var proxyId: Optional<UUID> = Optional.empty();
        var serverId: Optional<UUID> = Optional.empty();
    }
}