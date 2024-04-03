package ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import ui.navigation.BackStackState
import ui.navigation.Screen
import ui.navigation.ScreenTitle

@Single
class RunScreen : Screen, KoinComponent {
    companion object : ScreenTitle
    override val titleObj = Companion
//    override val vm: ActivitiesViewModel by inject()

    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) {
        Text("RunScreen; stack size: ${stack.size}")
    }
}