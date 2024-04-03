package ui.screens.EditScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.getOrCreateScope
import org.koin.core.component.inject
import ui.navigation.BackStackState
import ui.navigation.Screen
import ui.navigation.ScreenTitle
import ui.navigation.ScreenVM

@Single
class EditScreen : ScreenVM<EditScreenVM>, KoinScopeComponent {
    companion object : ScreenTitle {

    }
    override val titleObj = Companion

    override val scope by lazy { createScope(this) }
    override val vm: EditScreenVM by inject()

    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) {
        val titles by vm.readAll().collectAsState(emptyList())
        LazyColumn(
            modifier = modifier
        ) {
            items(titles, key = { it.id }) {
                ListItem(headlineContent = {
                    Text(it.title)
                })
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
            onAddClick {}
        }

    }
}