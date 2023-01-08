package com.beksar.kmm.architecture.core

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [Failure] class.
 */
sealed class Failure(override val message: String) : Throwable() {
    class Message(message: String) : Failure(message)
    object InternetConnection : Failure("Нет подключения к интернету")
}