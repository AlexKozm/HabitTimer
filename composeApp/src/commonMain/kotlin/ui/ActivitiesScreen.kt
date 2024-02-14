package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import data.activities.dto.ActivityView
import viewModels.ActivitiesViewModel

@Composable
fun ActivityScreen(
    activityViews: List<ActivityView>,
    switchRunningStatus: (ActivityView) -> Unit
) {
    activityViews.forEach { activityView ->
        ActivityElement(
            activityView = activityView,
            onClick = { switchRunningStatus(activityView) }
        )
    }
}