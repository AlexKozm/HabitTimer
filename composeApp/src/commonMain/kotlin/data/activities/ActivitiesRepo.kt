package data.activities

import data.activities.dto.ActivityRecord
import data.activities.dto.ActivityTitle
import data.activities.dto.ActivityTitleWithDuration
import data.activities.dto.ActivityView
import data.period
import data.now
import data.startOfTheData
import data.timer
import data.today
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
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
                        activityRecord.period()?.let { duration -> acc + duration } ?: acc
                    }
                }

            titlesToDurations.map { (title, duration) ->
                ActivityTitleWithDuration(title, duration)
            }
        }
    }

    private fun Flow<List<ActivityRecord>>.splitCompleteAndOngoingActivities() = run {
        val complete = map { it.filter { it.endTime != null } }
        val ongoing = map { it.filter { it.endTime == null } }
        complete to ongoing
    }

    private fun Map<ActivityTitle, List<ActivityRecord>>.sumUpPeriods(
        endIfNull: LocalDateTime
    ) = mapValues { it.value.fold(DateTimePeriod()) { acc, record ->
        acc + record.period(endIfNull)
    } }

    private fun Flow<List<ActivityTitle>>.combineToMap(completeRecords: Flow<List<ActivityRecord>>) =
        combine(completeRecords) { titles, activities ->
            titles.associateWith { DateTimePeriod() } +
                    activities.groupBy { it.activityTitle }.sumUpPeriods(now)
        }

    private fun Flow<List<ActivityRecord>>.combineToMapTimer(timer: Flow<LocalDateTime>) =
        combine(timer) { records, now ->
            records.groupBy { it.activityTitle }.sumUpPeriods(now)
        }

    fun testReadActivitiesInRange() = activitiesDS.readActivitiesInRange(
        (today.minus(DatePeriod(days = 1))).startOfTheData(),
        now
    )

    fun readActivitiesViews() = run {
        val allTitles = activitiesDS.readAllTitles()
        val (complete, ongoing) =
            activitiesDS.readActivitiesAfter(today.startOfTheData())
                .splitCompleteAndOngoingActivities()

        val completePart = allTitles.combineToMap(complete)
        val ongoingPart = ongoing.combineToMapTimer(timer)

        completePart.combine(ongoingPart) { completeRecords, ongoingRecords ->
            completeRecords.mapValues { it.value to false }.toMutableMap().apply {
                ongoingRecords.forEach { (title, period) ->
                    put(title, get(title)?.let { (it.first + period) to true }
                            ?: ((DateTimePeriod() + period) to true)
                    )
                }
            }.map {
                ActivityView(ActivityTitleWithDuration(it.key, it.value.first), it.value.second)
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

    suspend fun updateTitle(id: Long, title: String) = activitiesDS.updateTitleById(
        id = id,
        title = title
    )

    fun readAllByTitleId(id: Long) = activitiesDS.readAllRecordsByTitleId(id)

    suspend fun deleteActivity(id: Long) = activitiesDS.deleteTitleByIdWithAllRecords(id)

    suspend fun endRecordByTitleId(titleId: Long) = activitiesDS
        .updateRecordWithNullEndTimeByTitleId(titleId, now)

    fun readAllTitles() = activitiesDS.readAllTitles()
}