package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.getScopeOrNull
import org.koin.ext.getFullName
import viewModels.CommonViewModel

interface ScreenTitle {
    val title get() = this::class.getFullName()
}
interface Screen: ScreenTitle, KoinScopeComponent {
    override val scope get() = getScopeOrNull() ?: createScope()

    @Composable
    fun screen(modifier: Modifier, stack: BackStackState)
}

interface ScreenVM<VM: CommonViewModel> : Screen {
    val vm: VM

    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState)
}


@Composable
fun rememberNavState() = koinInject<BackStackState>()


@Composable
fun NavFun(
    modifier: Modifier,
    backStackState: BackStackState
) {
    backStackState.activeScreen.screen(modifier, backStackState)
}

@Composable
fun NavFun(
    modifier: Modifier,
    backStackState: BackStackState,
    lam: (activeScreen: Screen) -> Unit
) {
    lam(backStackState.activeScreen)
}

class BackStackState(
    private val initScreen: Screen,
    var findBeforeAdd: Boolean = true
): KoinComponent {
    private val stack = ArrayDeque(listOf(initScreen))
    val size get() = stack.size

    var activeScreen by mutableStateOf(stack.last())
        private set

    fun pop() {
        if (stack.size > 1) {
            stack.removeLast().scope.close()
        } else {
            stack.add(initScreen)
            stack.removeFirst().scope.close()
        }
        activeScreen = stack.last()
    }

    fun push(screen: Screen) {
        if (findBeforeAdd) {
            val res = stack.indexOfLast { screen.title == it.title }
            if (res < 0) stack.addLast(screen)
            else for (i in stack.size - 1 downTo res + 1) {
                stack.removeAt(i).scope.close()
            }
        } else stack.addLast(screen)
        activeScreen = stack.last()
    }



}