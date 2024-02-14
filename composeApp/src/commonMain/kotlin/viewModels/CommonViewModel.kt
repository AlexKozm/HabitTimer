package viewModels

import kotlinx.coroutines.CoroutineScope

expect open class CommonViewModel() {
    val viewModelScope: CoroutineScope
    protected fun onCleared()
}