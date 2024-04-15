package ui.screens.RunScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.DateTimePeriod
import org.koin.core.component.KoinComponent
import ui.navigation.BackStackState
import ui.navigation.ScreenVM


object RunScreen : ScreenVM<RunScreenVM>, KoinComponent {
    override val vm get() = scope.get<RunScreenVM>()

    fun DateTimePeriod.toStr() = "$hours:$minutes:$seconds"

    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) {
        val titles by vm.readActivities().collectAsState(emptyList())
        LazyColumn(
            modifier = modifier
        ) {
            items(titles, key = { it.titleId }) {
                ListItem(
                    modifier = Modifier.clickable { vm.toggleActivity(it) },
                    headlineContent = {
                        Text(it.title)
                    },
                    trailingContent = {
                        Text(it.duration.toStr())
                    },
                    colors = if (!it.running) ListItemDefaults.colors() else
                        ListItemDefaults.colors(trailingIconColor = Color.Green)
                )
            }
        }
    }
}