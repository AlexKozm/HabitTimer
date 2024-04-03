package ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import habittimer.composeapp.generated.resources.Res
import habittimer.composeapp.generated.resources.outline_bar_chart_24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.navigation.BackStackState
import ui.navigation.Screen
import ui.navigation.ScreenTitle

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RowScope.BottomNavItem(
    iconRes: DrawableResource,
    text: String,
    backStackState: BackStackState,
    screen: Screen
) {
    NavigationBarItem(
        icon = { Icon(
            painter = painterResource(iconRes),
            contentDescription = text
        ) },
        label = { Text(text = text) },
        onClick = { backStackState.push(screen) },
        selected = backStackState.activeScreen.title == screen.title
    )
}