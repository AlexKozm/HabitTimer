package data.activities

import data.activities.dto.ActivityTitleWithDuration
import data.duration
import data.now
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import org.koin.core.annotation.Single

@Single
class ActivitiesRepo(
    private val activitiesDS: ActivitiesDS
) {
    fun readTodayFinishedActivities(date: LocalDate) = run {
        val time = LocalTime(0, 0)
        val titlesFlow = activitiesDS.readAllTitles()
        val activitiesFlow = activitiesDS.readActivitiesInRange(
            LocalDateTime(date, time),
            LocalDateTime(date + DatePeriod(days = 1), time)
        )

        titlesFlow.zip(activitiesFlow) { titles, activities ->
            val titlesToZeroDurations = buildMap {
                titles.toSet().forEach { put(it, DateTimePeriod()) }
            }

            val titlesToDurations =
                titlesToZeroDurations + activities.groupBy { it.activityTitle }
                .mapValues { (_, activityRecords) ->
                    activityRecords.fold(DateTimePeriod()) { acc, activityRecord ->
                        activityRecord.duration()?.let { duration -> acc + duration } ?: acc
                    }
                }

            titlesToDurations.map { (title, duration) ->
                ActivityTitleWithDuration(title, duration)
            }
        }
    }

    suspend fun createActivity(title: String) = activitiesDS.createTitle(title)

    fun readTitlesOfRunningActivities() = activitiesDS.readRunningActivities().map { list ->
        list.map { it }.toSet()
    }

    suspend fun startRecordByTitleId(titleId: Long) = activitiesDS.createRecord(
        titleId = titleId,
        startTime = now,
        endTime = null
    )

    suspend fun endRecordByTitleId(titleId: Long) = activitiesDS
        .updateRecordWithNullEndTimeByTitleId(titleId, now)

    fun readAllTitles() = activitiesDS.readAllTitles()
}