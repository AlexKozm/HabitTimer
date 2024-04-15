package ui.screens.RunScreen

import data.activities.ActivitiesRepo
import data.activities.dto.ActivityView
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import viewModels.CommonViewModel

@Scope(RunScreen::class)
@Scoped
class RunScreenVM(
    private val activitiesRepo: ActivitiesRepo
) : CommonViewModel() {
    fun readActivities() = activitiesRepo.readActivitiesViews()

    fun toggleActivity(activityView: ActivityView) = viewModelScope.launch {
        if (activityView.running)
            activitiesRepo.endRecordByTitleId(activityView.titleId)
        else activitiesRepo.startRecordByTitleId(activityView.titleId)
    }
}