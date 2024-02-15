package di

import DriverFactory
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
@ComponentScan
class MySharedModule

//fun appModule() = module {
//    modules
//}

val sharedModule = module {
    includes(MySharedModule().module)
//    single {  }
}