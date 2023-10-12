package com.mc8s.service.player.api

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class PlayerModelNaiveImpl(
        @JsonProperty("uuid")
        private var uuid: UUID,

        @JsonProperty("online")
        private var online: Boolean
) : PlayerModel {

    override fun getUuid(): UUID {
        return uuid
    }

    override fun isOnline(): Boolean {
        return this.online
    }
}