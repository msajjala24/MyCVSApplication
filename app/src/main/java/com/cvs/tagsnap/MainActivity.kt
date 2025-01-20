package com.cvs.tagsnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cvs.tagsnap.ui.theme.MyCVSApplicationTheme
import com.cvs.tagsnap.ui.views.MyApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCVSApplicationTheme {
                MyApp()
            }
        }
    }
}