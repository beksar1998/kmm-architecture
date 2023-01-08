package com.beksar.kmm.architecture.presentation

import com.beksar.kmm.architecture.common.flow.asCommonFlow
import com.beksar.kmm.architecture.core.Either
import com.beksar.kmm.architecture.core.Failure
import com.beksar.kmm.platform.viewmodel.PlatformViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Action : BaseAction, State : BaseState, Event : BaseEvent> :
    PlatformViewModel() {

    protected val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val defaultModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())


    /**
     * Обработка action
     */
    open fun dispatch(action: BaseAction) {}

    /**
     * State — "устойчивые" данные об UI, которые должны быть показаны даже после пересоздания View
     */
    private val _state = MutableSharedFlow<BaseState?>(5, 5)
    val state = _state.filterNotNull().asCommonFlow()


    /**
     * Статус загрузки(launchOperation переключает)
     */
    private val _status = MutableSharedFlow<Boolean>()
    val status = _status.filterNotNull().asCommonFlow()


    /**
     * Action — "неустойчивые" данные об UI, которые не должны быть показаны после пересоздания
     * View (например, данные о Snackbar и Toast)
     */
    private val _event = Channel<BaseEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow().asCommonFlow()

    /**
     * Смена стейта
     */
    protected fun setState(newState: BaseState) {
        viewModelScope.launch {
            _state.emit(newState)
        }

    }


    protected fun setEvent(newEvent: BaseEvent) {
        viewModelScope.launch {
            _event.send(newEvent)
        }
    }

    protected fun setStatus(status: Boolean) {
        viewModelScope.launch {
            _status.emit(status)
        }
    }

    /**
     * Ошибки наследованные от Failure
     */
    private val _error = Channel<Failure?>(Channel.BUFFERED)
    val error = _error.receiveAsFlow().filterNotNull().asCommonFlow()

    /**
     * Показать ошибку
     */
    protected fun setError(error: String) {
        viewModelScope.launch {
            _status.emit(false)
            _error.send(Failure.Message(error))
            delay(100)
            _error.send(null)
        }
    }

    /**
     * Выполнить запросы
     * @param operation - описываем запрос
     * @param loading - статус загрузки(можем переопределить)
     * @param failure - подписка на ошибку(можем переопределить)
     * @param success - результат
     */
    protected fun <T> launchOperation(
        operation: suspend (CoroutineScope) -> Either<Failure, T>,
        loading: (Boolean) -> Unit = { setStatus(it) },
        failure: (Failure) -> Unit = { handleError(it) },
        success: (T) -> Unit = {},
    ): Job {
        return viewModelScope.launch(handler) {
            loading.invoke(true)
            withContext(defaultModelScope.coroutineContext) {
                operation(this)
            }.fold(failure, success)
            loading.invoke(false)
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        if (exception.message?.contains("host", true) == true) {
            handleError(Failure.InternetConnection)
        } else {
            handleError(exception)
        }
    }

    protected fun handleError(failure: Throwable) {
        setError(failure.message.orEmpty())
    }


}