package com.beksar.kmm.architecture.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform