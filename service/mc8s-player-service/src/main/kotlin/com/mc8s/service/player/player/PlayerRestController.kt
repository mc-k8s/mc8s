package com.mc8s.service.player.player

import com.mc8s.service.player.api.PlayerApi
import com.mc8s.service.player.api.PlayerModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class PlayerRestController(
        private val persistentDataRepository: PlayerPersistentDataRepository,
        private val volatileDataRepository: PlayerVolatileDataRepository,
) : PlayerApi {

//    @GetMapping("/count")
//    fun count(): Int {
//        return this.persistentDataRepository.countPlayersByOnlineStateTrue()
//    }

    @GetMapping("/{uuid}")
    override fun getPlayer(@PathVariable("uuid") uuid: UUID): PlayerModel {
        val persistentData = this.persistentDataRepository.findById(uuid);
        val volatileData = this.volatileDataRepository.getPlayer(uuid)

        if (persistentData.isEmpty) {
            TODO()
        }


        return PlayerModelImpl.join(volatileData, persistentData.get())
    }


    @PostMapping("")
    override fun registerPlayer(@RequestBody player: PlayerModel) {
        if (this.persistentDataRepository.findById(player.getUuid()).isPresent) {
            TODO()
        } else {
            this.persistentDataRepository.save(PlayerPersistentData.from(player))
            this.volatileDataRepository.save(player.getUuid(), PlayerVolatileData.from(player))
        }
    }

//    @PatchMapping("/")
//    fun patch(@RequestBody patch: PlayerEntity.Patch) {
//        val player = this.playerRepository.findById(patch.uuid).get()
//        player.patch(patch)
//        this.playerRepository.save(player)
//    }
}