package com.an7one.officialDoc.jetpackCompose.codeLabAnimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.an7one.officialDoc.jetpackCompose.codeLabAnimation.ui.page.Home
import com.an7one.officialDoc.jetpackCompose.codeLabAnimation.ui.theme.ThemeCodeLabAnimation
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThemeCodeLabAnimation {
                Home()
            }
        }

//        if (BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())
    }
}