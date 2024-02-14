package data.activities.dto

import kotlinx.datetime.DateTimePeriod

data class ActivityView(
    val activityTitleWithDuration: ActivityTitleWithDuration,
    val running: Boolean
) {
    constructor(id: Long, title: String, duration: DateTimePeriod, running: Boolean) :
            this(ActivityTitleWithDuration(ActivityTitle(id, title), duration), running)
    val title get() = activityTitleWithDuration.title
    val titleId get() = activityTitleWithDuration.titleId
    val duration get() = activityTitleWithDuration.time
}