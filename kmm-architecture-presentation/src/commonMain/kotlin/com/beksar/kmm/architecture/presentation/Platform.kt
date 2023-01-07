package com.beksar.kmm.architecture.presentation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform