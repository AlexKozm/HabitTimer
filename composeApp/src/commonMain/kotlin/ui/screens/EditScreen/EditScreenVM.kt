package ui.screens.EditScreen

import data.activities.ActivitiesRepo
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import org.koin.core.annotation.Single
import viewModels.CommonViewModel

@Scope(EditScreen::class)
@Scoped
class EditScreenVM(
    private val activitiesRepo: ActivitiesRepo
): CommonViewModel() {
    fun readAll() = activitiesRepo.readAllTitles()
    fun addActivity(title: String) = viewModelScope.launch {
        activitiesRepo.createActivity(title)
    }



}