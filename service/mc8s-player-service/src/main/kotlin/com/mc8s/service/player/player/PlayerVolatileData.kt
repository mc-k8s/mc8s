package com.mc8s.service.player.player

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.mc8s.service.player.api.PlayerModel
import java.io.Serializable

@JsonSerialize
class PlayerVolatileData(
        var online: Boolean = false
) : Serializable {
    companion object {
        fun from(playerModel: PlayerModel): PlayerVolatileData = PlayerVolatileData(
                playerModel.isOnline()
        )
    }
}