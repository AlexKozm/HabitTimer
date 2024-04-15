package data

import data.activities.dto.ActivityRecord
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toDateTimePeriod
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal fun LocalDateTime.toMillis() = toInstant(TimeZone.currentSystemDefault())
    .toEpochMilliseconds()

internal fun Long.fromMillisToLocalDateTime() = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault())

val now get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

val today get() = now.date

fun LocalDateTime.startOfTheData() = LocalDateTime(date, LocalTime(0, 0))
fun LocalDate.startOfTheData() = LocalDateTime(this, LocalTime(0, 0))


internal val curTimeZone get() = TimeZone.currentSystemDefault()

internal fun dateTimePeriod(start: LocalDateTime?, end: LocalDateTime?) = start?.let { end?.let {
    end.toInstant(curTimeZone) - start.toInstant(curTimeZone)
} }?.toDateTimePeriod()

val timer get() = flow {
    while (true) {
        delay(10)
        emit(now)
    }
}

internal fun ActivityRecord.period() = dateTimePeriod(startTime, endTime)

internal fun ActivityRecord.period(end: LocalDateTime) =
    ((endTime?:end).toInstant(curTimeZone) - startTime.toInstant(curTimeZone)).toDateTimePeriod()


internal fun ActivityRecord.duration(end: LocalDateTime) =
    ((endTime?:end).toMillis() - startTime.toMillis()).toDuration(DurationUnit.MILLISECONDS)