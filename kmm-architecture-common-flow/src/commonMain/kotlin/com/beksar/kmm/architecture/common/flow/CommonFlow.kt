package com.beksar.kmm.architecture.common.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {

    fun collect(block: (T) -> Unit): Closeable {
        val job = Job()
        onEach { block(it) }.launchIn(CoroutineScope(job + Dispatchers.Main))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}