package ui.screens.editScreen.addOrEditActivity.addActivity

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.navigation.BackStackState
import ui.navigation.ScreenVM
import ui.screens.editScreen.addOrEditActivity.AddOrEditScreen

object AddActivityScreen: ScreenVM<AddActivityVM> {
    override val vm get() = scope.get<AddActivityVM>()

    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState) =
        vm.AddOrEditScreen(modifier, stack)
}