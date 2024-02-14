package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import viewModels.ActivitiesViewModel

@Composable
fun Navigator(activitiesViewModel: ActivitiesViewModel) {
    val activityViews by activitiesViewModel
        .activitiesView.collectAsState(emptyList())
    ActivityScreen(
        activityViews = activityViews,
        switchRunningStatus = { activityView -> with(activityView) {
            if (running) activitiesViewModel.stopRecordByTitleId(titleId)
            else activitiesViewModel.startRecordByTitleId(titleId)
        } }
    )
}