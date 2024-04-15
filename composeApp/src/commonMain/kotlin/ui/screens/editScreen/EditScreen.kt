package ui.screens.editScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ui.navigation.BackStackState
import ui.navigation.ScreenVM
import ui.screens.editScreen.addOrEditActivity.editActivity.EditActivityScreen
import ui.screens.editScreen.addOrEditActivity.addActivity.AddActivityScreen


object EditScreen : ScreenVM<EditScreenVM> {

//    val lazyScope
//    override val scope get() = getScopeOrNull() ?: createScope()
    override val vm: EditScreenVM get() = scope.get<EditScreenVM>()

    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) {
        val titles by vm.readAll().collectAsState(emptyList())
        LazyColumn(
            modifier = modifier
        ) {
            items(titles, key = { it.id }) {
                ListItem(
                    modifier = Modifier.clickable { stack.push(EditActivityScreen.get(it)) },
                    headlineContent = {
                        Text(it.title)
                    }
                )
            }
        }
    }

    @Composable
    fun screen(
        onAddClick: ( (() -> Unit)? ) -> Unit,
        modifier: Modifier,
        stack: BackStackState
    ) {
        LaunchedEffect(Unit) {
            onAddClick { stack.push(AddActivityScreen) }
        }
        screen(modifier, stack)
    }
}