package data.activities.dto

import kotlinx.datetime.LocalDateTime

data class ActivityRecord(
    val activityTitle: ActivityTitle,
    val recordId: Long,
    val endTime: LocalDateTime?,
    val startTime: LocalDateTime
) {
    val title get() = activityTitle.title
    val titleId get() = activityTitle.id
}