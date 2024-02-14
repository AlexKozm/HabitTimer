package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.activities.dto.ActivityView

@Composable
fun ActivityElement(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    activityView: ActivityView
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(2F),
            text = activityView.title
        )
        Text(
            modifier = Modifier.weight(2F),
            text = activityView.duration.toString()
        )
        Button(
            modifier = Modifier.weight(1F),
            onClick = onClick
        ) {
            Text(activityView.running.toString())
        }
    }
}