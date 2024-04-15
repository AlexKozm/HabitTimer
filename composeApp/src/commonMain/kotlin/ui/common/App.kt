package ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import habittimer.composeapp.generated.resources.Res
import habittimer.composeapp.generated.resources.baseline_play_circle_outline_24
import habittimer.composeapp.generated.resources.outline_add_24
import habittimer.composeapp.generated.resources.outline_edit_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.navigation.rememberNavState
import ui.screens.AnalyticsScreen
import ui.screens.editScreen.EditScreen
import ui.screens.RunScreen.RunScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val navStackState = rememberNavState()
    val (fabOnClick, setFabOnClick) =
        remember { mutableStateOf<(() -> Unit)?>(null) }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(visible = navStackState.activeScreen == EditScreen) {
                FloatingActionButton(
                    onClick = { fabOnClick?.invoke() }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.outline_add_24),
                        contentDescription = "Add"
                    )
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = navStackState.activeScreen.let {
                    it == EditScreen || it == RunScreen || it == AnalyticsScreen
                }
            ) {
                NavigationBar {
                    BottomNavItem(
                        iconRes = Res.drawable.outline_edit_24,
                        text = "Edit",
                        backStackState = navStackState,
                        screen = EditScreen
                    )
                    BottomNavItem(
                        iconRes = Res.drawable.baseline_play_circle_outline_24,
                        text = "Run",
                        backStackState = navStackState,
                        screen = RunScreen
                    )
                    BottomNavItem(
                        iconRes = Res.drawable.outline_edit_24,
                        text = "Analytics",
                        backStackState = navStackState,
                        screen = AnalyticsScreen
                    )
                }
            }
        }
    ) { padding ->
//        NavFun(
//            modifier = Modifier.padding(padding),
//            backStackState = navStackState
//        )


//        NavFun(
//            modifier = Modifier.padding(padding),
//            backStackState = navStackState
//        ) { when(it) {
//            is EditScreen -> {}
//        } }

        val modifier = Modifier.padding(padding)
        val a = when(val activeScreen = navStackState.activeScreen) {
            is EditScreen -> activeScreen.screen(
                onAddClick = setFabOnClick,
                modifier, navStackState
            )
            else -> activeScreen.screen(modifier, navStackState)
        }
    }
}