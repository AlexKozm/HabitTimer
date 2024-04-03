package ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import habittimer.composeapp.generated.resources.Res
import habittimer.composeapp.generated.resources.baseline_play_circle_outline_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import viewModels.ActivitiesViewModel


@Single
class MainScreen : ScreenVM<ActivitiesViewModel>, KoinComponent {
    companion object : ScreenTitle
    override val titleObj = Companion
    override val vm: ActivitiesViewModel by inject()

    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = { stack.push(get<EditScreen>()) }
            ) {
                Text(text = "To EditScreen")
            }
        }
    }
}

@Single
class EditScreen : Screen, KoinComponent {
    companion object : ScreenTitle
    override val titleObj = Companion

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = { stack.push(get<MainScreen>()) }
            ) {
                Text(text = "To MainScreen")
            }
            Icon(painter = painterResource(Res.drawable.baseline_play_circle_outline_24), "")
        }
    }

}