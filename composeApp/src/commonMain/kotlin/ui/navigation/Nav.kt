package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.koin.compose.rememberKoinInject
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.ext.getFullName
import viewModels.CommonViewModel

interface ScreenTitle {
    val title get() = this::class.getFullName()
}
interface Screen {
    val titleObj: ScreenTitle

    val title get() = titleObj.title

    @Composable
    fun screen(modifier: Modifier, stack: BackStackState)
}

interface ScreenVM<VM: CommonViewModel> : Screen {
    val vm: VM
    @Composable
    override fun screen(modifier: Modifier, stack: BackStackState)/* =
        screen(modifier, vm, stack)*/
//    @Composable
//    fun screen(modifier: Modifier, vm: VM, stack: BackStackState<Screen>)
}

//@Composable
//fun rememberNavState(initScreen: Screen) = remember {
////    koinInject<BackStackState<Screen>>()
//    BackStackState(initScreen)
//}

@Composable
fun rememberNavState(initScreen: Screen) = koinInject<BackStackState>()

@Composable
fun NavFun(
    modifier: Modifier,
    initScreen: Screen,
    backStackState: BackStackState = rememberNavState(initScreen)
) {
    backStackState.activeScreen.screen(modifier, backStackState)
}

@Composable
fun NavFun(
    modifier: Modifier,
    backStackState: BackStackState
) {
    backStackState.activeScreen.screen(modifier, backStackState)
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
            stack.removeLast()
        } else {
            stack.add(initScreen)
            stack.removeFirst()
        }
        activeScreen = stack.last()
    }




    fun push(screen: Screen) {
        if (findBeforeAdd) {
            val res = stack.indexOfLast { screen.title == it.title }
            if (res < 0) stack.addLast(screen)
            else for (i in stack.size - 1 downTo res + 1) {
//                try {
                    stack.removeAt(i)
//                } catch (_: IndexOutOfBoundsException) {}
            }

        } else stack.addLast(screen)
        activeScreen = stack.last()
    }

}