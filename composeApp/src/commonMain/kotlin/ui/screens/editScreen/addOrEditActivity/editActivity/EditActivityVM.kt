package ui.screens.editScreen.addOrEditActivity.editActivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.activities.ActivitiesRepo
import data.activities.dto.ActivityTitle
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import ui.screens.editScreen.addOrEditActivity.AddOrEditActivityVM
import viewModels.CommonViewModel

@Scope(EditActivityScreen::class)
@Scoped
class EditActivityVM(
    private val activitiesRepo: ActivitiesRepo
) : CommonViewModel(), AddOrEditActivityVM {
    override var tmpTitle: String by mutableStateOf("")
    lateinit var activityToEdit: ActivityTitle
    override fun saveActivity() = viewModelScope.launch {
        if (::activityToEdit.isInitialized)
            activitiesRepo.updateTitle(activityToEdit.id, tmpTitle)
    }.let { }

    val readAllActivitiesRecords get() = activitiesRepo.readAllByTitleId(activityToEdit.id)

    fun deleteActivity() = viewModelScope.launch {
        if(::activityToEdit.isInitialized)
            activitiesRepo.deleteActivity(activityToEdit.id)
    }
}