package viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope as vmScope
import kotlinx.coroutines.CoroutineScope

actual open class CommonViewModel actual constructor() : ViewModel() {
    actual val viewModelScope: CoroutineScope = vmScope
    actual override fun onCleared() {
        super.onCleared()
    }

}