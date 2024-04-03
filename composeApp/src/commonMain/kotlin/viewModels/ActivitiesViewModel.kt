package viewModels

import data.activities.ActivitiesRepo
import data.activities.dto.ActivityTitleWithDuration
import data.activities.dto.ActivityView
import data.dateTimePeriod
import data.now
import data.today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime


class ActivitiesViewModel(
    private val activitiesRepo: ActivitiesRepo
) : CommonViewModel() {

    private val titlesWithActivitySum =
        UpdatableFlow<List<ActivityTitleWithDuration>>(viewModelScope)

    private val runningActivities = activitiesRepo.readTitlesOfRunningActivities()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    private val timerFlow =
        flow { while (true) {
            emit(now)
            delay(100)
        } }.stateIn(viewModelScope, SharingStarted.Eagerly, now)

    val activitiesView = combine(runningActivities, titlesWithActivitySum.stateFlow, timerFlow)
    { running, activityTitlesWithDurations, timer ->
        activityTitlesWithDurations?.map { activityTitleWithDuration ->
            val startTimes = running
                .associateBy({ it.activityTitle }, { it.startTime })
            val title = activityTitleWithDuration.activityTitle
            val runningTitles = running.map { it.activityTitle }
            if (runningTitles.contains(title)) {
                val currentDuration = activityTitleWithDuration.copy(
                    time = dateTimePeriod(startTimes[title], timer) ?: DateTimePeriod())
                ActivityView(currentDuration, true)
            } else {
                ActivityView(activityTitleWithDuration, false)
            }
        }?.sortedBy { activityView -> activityView.title } ?: emptyList()
    }

    fun startRecordByTitleId(titleId: Long) = viewModelScope.launch {
        if (titleId !in runningActivities.value.map { it.titleId })
            activitiesRepo.startRecordByTitleId(titleId)
    }

    fun stopRecordByTitleId(titleId: Long) = viewModelScope.launch {
        if (titleId in runningActivities.value.map { it.titleId })
            activitiesRepo.endRecordByTitleId(titleId)
    }

    fun createActivity(title: String) = viewModelScope.launch {
        activitiesRepo.createActivity(title)
    }

    fun readAllTitles() = activitiesRepo.readAllTitles()
}