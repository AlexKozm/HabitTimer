package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import viewModels.ActivitiesViewModel

@Composable
fun Navigator(/*activitiesViewModel: ActivitiesViewModel*/) {
    val activitiesViewModel = koinInject<ActivitiesViewModel>()
    LaunchedEffect(Unit) {
//        activitiesViewModel.createActivity("TOEFL")
    }
    val activityViews by activitiesViewModel
        .activitiesView.collectAsState(emptyList())

    val allTitles by activitiesViewModel
        .readAllTitles().collectAsState(emptyList())
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(allTitles) {
            Text(text = it.title)
        }
    }
//    ActivityScreen(
//        activityViews = activityViews,
//        switchRunningStatus = { activityView -> with(activityView) {
//            if (running) activitiesViewModel.stopRecordByTitleId(titleId)
//            else activitiesViewModel.startRecordByTitleId(titleId)
//        } }
//    )
}