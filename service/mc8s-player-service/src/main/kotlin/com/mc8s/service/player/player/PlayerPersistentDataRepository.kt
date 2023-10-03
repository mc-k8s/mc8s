package com.mc8s.service.player.player

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PlayerPersistentDataRepository : JpaRepository<PlayerPersistentData, UUID> {
//  fun countPlayersByOnlineStateTrue(): Int
}