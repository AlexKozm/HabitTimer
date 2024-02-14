package data

import data.activities.dto.ActivityRecord
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toDateTimePeriod
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

internal fun LocalDateTime.toMillis() = toInstant(TimeZone.currentSystemDefault())
    .toEpochMilliseconds()

internal fun Long.fromMillisToLocalDateTime() = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault())

val now get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

val today get() = now.date

internal val curTimeZone get() = TimeZone.currentSystemDefault()

internal fun dateTimePeriod(start: LocalDateTime?, end: LocalDateTime?) = start?.let { end?.let {
    start.toInstant(curTimeZone) - end.toInstant(curTimeZone)
} }?.toDateTimePeriod()



internal fun ActivityRecord.duration() = dateTimePeriod(startTime, endTime)