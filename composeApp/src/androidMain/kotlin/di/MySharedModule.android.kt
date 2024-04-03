package di


import DriverFactory
import com.example.Database
import createDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

actual fun platformModule() = module {
    single<DriverFactory> { DriverFactory(androidContext()) }
//    single<Database> { createDatabase(get<DriverFactory>()) }
}