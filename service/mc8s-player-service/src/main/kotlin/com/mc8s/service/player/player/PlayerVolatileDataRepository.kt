package com.mc8s.service.player.player

import com.hazelcast.core.HazelcastInstance
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID
import java.util.concurrent.TimeUnit

@Repository
class PlayerVolatileDataRepository(
        private val hazelcastInstance: HazelcastInstance
) {
    private val volatilePlayerData = hazelcastInstance.getReplicatedMap<UUID, PlayerVolatileData>("com.mc8s.service.player.volatile-data")

    fun getPlayer(uuid: UUID): PlayerVolatileData = this.volatilePlayerData.getOrDefault(uuid, PlayerVolatileData())

    fun save(uuid: UUID, volatileData: PlayerVolatileData) = this.volatilePlayerData.put(uuid, volatileData, 15, TimeUnit.SECONDS)
}