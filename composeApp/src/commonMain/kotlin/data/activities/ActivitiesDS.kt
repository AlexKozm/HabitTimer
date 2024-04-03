package data.activities

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.example.ActivitiesTable
import com.example.ActivitiesTitlesTable
import com.example.Database
import data.activities.dto.ActivityRecord
import data.activities.dto.ActivityTitle
import data.fromMillisToLocalDateTime
import data.toMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Single
import kotlin.coroutines.CoroutineContext

@Single
class ActivitiesDS(
    private val externalContext: CoroutineContext = Dispatchers.IO,
    private val database: Database
) {
    private val recordsQueries = database.activitiesRecordsQueries
    private val titlesQueries = database.activitiesTitlesQueries
    private val activitiesQueries = database.activitiesQueries
    private val commonQueries = database.commonQueries

    suspend fun createTitle(title: String) = withContext(externalContext) {
        database.transactionWithResult {
            titlesQueries.createTitle(title)
            commonQueries.lastInsertRowId().executeAsOne()
        }
    }

    suspend fun updateTitleById(id: Long, title: String) = withContext(externalContext) {
        titlesQueries.updateTitleById(title, id)
    }

    suspend fun deleteTitleByIdWithAllRecords(id: Long) = withContext(externalContext) {
        titlesQueries.deleteTitleAndAllRecordsByTitleId(id)
    }

    suspend fun createRecord(titleId: Long, startTime: LocalDateTime, endTime: LocalDateTime?) =
        withContext(externalContext) {
            recordsQueries.createRecord(titleId, startTime.toMillis(), endTime?.toMillis())
        }

    suspend fun updateRecordById(id: Long, startTime: LocalDateTime, endTime: LocalDateTime?) =
        withContext(externalContext) {
            recordsQueries.updateRecordById(startTime.toMillis(), endTime?.toMillis(), id)
        }

    suspend fun deleteRecordById(id: Long) = withContext(externalContext) {
        recordsQueries.deleteRecord(id)
    }

    suspend fun updateRecordWithNullEndTimeByTitleId(titleId: Long, endTime: LocalDateTime) =
        withContext(externalContext) {
            recordsQueries.updateRecordWithNullEndTimeByTitleId(endTime.toMillis(), titleId)
        }

    fun readByTitleIdInRange(
        titleIds: List<Long>,
        startTime: LocalDateTime,
        endTime: LocalDateTime?
    ) = activitiesQueries.readByTitleIdsBetween(
            titleIds, startTime.toMillis(), endTime?.toMillis()
        )

    fun readActivitiesInRange(
        startTime: LocalDateTime,
        endTime: LocalDateTime?
    ) = activitiesQueries.readActivitiesBetween(startTime.toMillis(), endTime?.toMillis())
        .asFlow().mapToList(externalContext).map { list -> list.map { activityRecord ->
            activityRecord.asActivityRecord()
        } }

    fun readAllTitles() = titlesQueries.readAllTItles().asFlow().mapToList(externalContext)
        .map { list -> list.map { title -> title.asActivityTitle() } }

    fun readAllByTitleId(titleId: Long) = activitiesQueries.readAllByTitleId(titleId)
        .executeAsList()

    fun readRunningActivities() = activitiesQueries.readRunningActivities().asFlow()
        .mapToList(externalContext).map { list -> list.map { record -> record.asActivityRecord() } }

    private fun ActivitiesTitlesTable.asActivityTitle() = ActivityTitle(
        id = id,
        title = title
    )

    private fun ActivitiesTable.asActivityRecord() = ActivityRecord(
        activityTitle = ActivityTitle(titleId, title),
        recordId = recordId,
        endTime = endTime?.fromMillisToLocalDateTime(),
        startTime = startTime.fromMillisToLocalDateTime()
    )

}