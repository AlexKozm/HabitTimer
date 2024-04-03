package ui.preview

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import data.activities.dto.ActivityView
import kotlinx.datetime.DateTimePeriod
import ui.ActivityScreen
import ui.common.App

@Preview
@Composable
fun ActivitiesScreenPreview() {
    val list = listOf(
        ActivityView(
            1,
            "title1",
            DateTimePeriod(0, 0, 0, 1),
            false)
    )
    ActivityScreen(list, { })
}