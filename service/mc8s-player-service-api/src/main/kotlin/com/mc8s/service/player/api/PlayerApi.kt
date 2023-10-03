package com.mc8s.service.player.api

import feign.Body
import feign.Feign
import feign.Param
import feign.RequestLine
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import java.util.UUID

interface PlayerApi {
    @RequestLine("GET /api/v1/users/{uuid}")
    fun getPlayer(@Param("uuid") uuid: UUID): PlayerModel

    @RequestLine("POST /api/v1/users")
    fun registerPlayer(player: PlayerModel);

    companion object {
        fun create(url: String): PlayerApi = Feign.builder()
                .encoder(JacksonEncoder())
                .decoder(JacksonDecoder())
                .requestInterceptor {
                    it.header("Content-Type", "application/json")
                }
                .target(PlayerApi::class.java, url)

    }
}