package com.beksar.architecture

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform