package com.basic

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BagicApplication

fun main(args: Array<String>) {
    runApplication<BagicApplication>(*args)
}
