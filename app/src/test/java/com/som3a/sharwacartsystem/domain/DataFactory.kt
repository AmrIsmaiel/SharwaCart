package com.som3a.sharwacartsystem.domain

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {
    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    }

    fun randomDouble(): Double {
        return ThreadLocalRandom.current().nextDouble(0.0, 1000.0 + 1)
    }
}