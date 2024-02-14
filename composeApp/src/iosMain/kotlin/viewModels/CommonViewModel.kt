package viewModels

import kotlinx.coroutines.CoroutineScope

actual open class CommonViewModel actual constructor() {
    actual val viewModelScope: CoroutineScope = TODO()
}