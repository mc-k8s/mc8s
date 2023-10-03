package com.mc8s.service.player.testdata

import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.opentable.db.postgres.embedded.EmbeddedPostgres
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
@Profile("test-data")
class TestDataConfiguration {
    @Bean
    @Primary
    fun inMemoryDataSource(): DataSource = EmbeddedPostgres.start().postgresDatabase

    @Bean
    @Primary
    fun inMemoryHazelcast(): HazelcastInstance = Hazelcast.newHazelcastInstance()
}