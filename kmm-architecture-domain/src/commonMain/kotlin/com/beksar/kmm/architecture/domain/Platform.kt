package com.beksar.kmm.architecture.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform