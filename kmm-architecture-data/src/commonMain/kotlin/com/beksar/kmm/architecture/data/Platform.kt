package com.beksar.kmm.architecture.data

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform