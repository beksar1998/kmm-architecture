package com.beksar.kmm.architecture.common.flow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform