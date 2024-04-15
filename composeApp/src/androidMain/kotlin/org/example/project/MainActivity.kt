package org.example.project

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.android.get
import ui.common.App
import ui.navigation.BackStackState
import ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme(
                useDarkTheme = true
            ) {
                App()
            }
        }
        onBackPressedDispatcher.addCallback { get<BackStackState>().pop() }

        val intent = Intent(this, StopActivitiesService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
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