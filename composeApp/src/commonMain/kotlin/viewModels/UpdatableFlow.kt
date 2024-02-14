package viewModels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UpdatableFlow<T>(
    private val coroutineScope: CoroutineScope
) {
    val stateFlow = MutableStateFlow<T?>(null)

    private var flowJob: Job? = null
    fun update(flow: Flow<T?>) = coroutineScope.launch {
        flowJob?.cancelAndJoin()
        flowJob = coroutineScope.launch {
            flow.collect { data ->
                stateFlow.value = data
            }
        }
    }
}