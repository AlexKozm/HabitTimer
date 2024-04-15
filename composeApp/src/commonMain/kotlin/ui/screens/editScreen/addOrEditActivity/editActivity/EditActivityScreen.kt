package ui.screens.editScreen.addOrEditActivity.editActivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import data.activities.dto.ActivityTitle
import kotlinx.datetime.LocalDateTime
import ui.navigation.BackStackState
import ui.navigation.ScreenVM
import ui.screens.editScreen.addOrEditActivity.AddOrEditScreen

class EditActivityScreen private constructor(): ScreenVM<EditActivityVM> {
    override val vm get() = scope.get<EditActivityVM>()

    companion object {
        private val obj = EditActivityScreen()
        fun get(activityTitle: ActivityTitle) = with(obj) { also {
            vm.activityToEdit = activityTitle
            vm.tmpTitle = activityTitle.title
        } }
    }
    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) {
        fun LocalDateTime.toStr() = "$date $hour:$minute:$second"
        Column(modifier = modifier) {
            vm.AddOrEditScreen(Modifier, stack)
            val records by vm.readAllActivitiesRecords.collectAsState(emptyList())
            LazyColumn {
                items(records, key = { it.recordId }) {
                    ListItem(
                        headlineContent = {
                            Text("${it.startTime.toStr()} \n ${it.endTime?.toStr()}")
                        }
                    )
                }
            }
        }



    }
}