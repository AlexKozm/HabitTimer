package ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import habittimer.composeapp.generated.resources.Res
import habittimer.composeapp.generated.resources.baseline_play_circle_outline_24
import habittimer.composeapp.generated.resources.outline_edit_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import ui.navigation.NavFun
import ui.navigation.rememberNavState
import ui.screens.AnalyticsScreen
import ui.screens.EditScreen.EditScreen
import ui.screens.EditScreen.EditScreenVM
import ui.screens.RunScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val navStackState = rememberNavState(koinInject<RunScreen>())
    val (fabOnClick, setFabOnClick) =
        remember { mutableStateOf<(() -> Unit)?>(null) }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { fabOnClick?.invoke() }
            ) {

            }
        },
        bottomBar = {
            NavigationBar {
                BottomNavItem(
                    iconRes = Res.drawable.outline_edit_24,
                    text = "Edit",
                    backStackState = navStackState,
                    screen = koinInject<EditScreen>()
                )
                BottomNavItem(
                    iconRes = Res.drawable.baseline_play_circle_outline_24,
                    text = "Run",
                    backStackState = navStackState,
                    screen = koinInject<RunScreen>()
                )
                BottomNavItem(
                    iconRes = Res.drawable.outline_edit_24,
                    text = "Analytics",
                    backStackState = navStackState,
                    screen = koinInject<AnalyticsScreen>()
                )
            }
        }
    ) { padding ->
        NavFun(
            modifier = Modifier.padding(padding),
            backStackState = navStackState
        )

//        val modifier = Modifier.padding(padding)
//        when(val activeScreen = navStackState.activeScreen) {
//            is EditScreen -> activeScreen.screen(
//                onAddClick = setFabOnClick
//            )
//            else -> activeScreen.screen(modifier, navStackState)
//        }
    }
}