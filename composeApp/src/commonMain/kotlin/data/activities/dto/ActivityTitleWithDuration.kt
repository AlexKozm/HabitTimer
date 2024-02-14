package data.activities.dto

import kotlinx.datetime.DateTimePeriod

data class ActivityTitleWithDuration(
    val activityTitle: ActivityTitle,
    val time: DateTimePeriod
) {
    val title get() = activityTitle.title
    val titleId get() = activityTitle.id
}