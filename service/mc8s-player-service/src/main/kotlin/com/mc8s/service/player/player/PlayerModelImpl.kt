package com.mc8s.service.player.player

import com.mc8s.service.player.api.PlayerModel
import java.util.*

class PlayerModelImpl(
        private val volatileData: PlayerVolatileData,
        private val persistentData: PlayerPersistentData
) : PlayerModel {
    companion object {
        fun join(volatileData: PlayerVolatileData, persistentData: PlayerPersistentData) = PlayerModelImpl(volatileData, persistentData)
    }

    override fun getUuid(): UUID = this.persistentData.uuid

    override fun isOnline(): Boolean = this.volatileData.online

}