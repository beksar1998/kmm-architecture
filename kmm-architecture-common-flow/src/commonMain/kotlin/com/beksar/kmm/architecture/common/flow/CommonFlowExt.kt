package com.beksar.kmm.architecture.common.flow

import kotlinx.coroutines.flow.Flow


fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)
