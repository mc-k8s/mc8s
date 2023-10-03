package com.mc8s.service.player.api

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.UUID

@JsonDeserialize(`as` = PlayerModelNaiveImpl::class)
interface PlayerModel {

    fun getUuid(): UUID;

    fun isOnline(): Boolean
}