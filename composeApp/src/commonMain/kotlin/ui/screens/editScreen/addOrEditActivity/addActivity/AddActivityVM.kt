package ui.screens.editScreen.addOrEditActivity.addActivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.activities.ActivitiesRepo
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import ui.screens.editScreen.addOrEditActivity.AddOrEditActivityVM
import viewModels.CommonViewModel

@Scope(AddActivityScreen::class)
@Scoped
class AddActivityVM(
    private val activitiesRepo: ActivitiesRepo
) : CommonViewModel(), AddOrEditActivityVM {
    override var tmpTitle: String by mutableStateOf("")
    override fun saveActivity() = createActivity()

    fun createActivity() = viewModelScope.launch {
        activitiesRepo.createActivity(tmpTitle)
    }.let { }
}