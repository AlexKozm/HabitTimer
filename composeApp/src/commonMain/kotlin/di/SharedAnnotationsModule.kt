package di

import com.example.Database
import createDatabase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module
import ui.navigation.BackStackState
import ui.screens.RunScreen.RunScreen
import viewModels.ActivitiesViewModel

//import org.koin.ksp.generated.defaultModule

@Module
@ComponentScan(".")
class SharedAnnotationsModule

//fun appModule() = module {
//    modules
//}

expect fun platformModule(): org.koin.core.module.Module

fun appModule() = module {
    includes(SharedAnnotationsModule().module, platformModule()/*, defaultModule*/)

    single<Database> { createDatabase(get()) }
    single { BackStackState(RunScreen) }
    single { ActivitiesViewModel(get()) }

//    single { get<DriverFactory>().createDriver() }
//    includes(defaultModule)
}


