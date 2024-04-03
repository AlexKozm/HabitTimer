package org.example.project

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import di.appModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ui.Navigator
import ui.common.App
import ui.navigation.MainScreen
import ui.navigation.NavFun
import ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val driver = DriverFactory(applicationContext).createDriver()


//        val onScreenOffIntentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
//        registerReceiver(object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                // Stop timer
//            }
//        }, onScreenOffIntentFilter)

//        val vm by inject<ActivitiesViewModel>()
        setContent {
            AppTheme(
                useDarkTheme = true
            ) {
                App()
//                NavFun(initScreen = get<MainScreen>())

            }
//            LandingScreen()
//            Navigator(/*activitiesViewModel = vm*/)
//            App()
        }

//        onBackPressedDispatcher.addCallback {  }

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity: onDestroy")
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
//    App()
}