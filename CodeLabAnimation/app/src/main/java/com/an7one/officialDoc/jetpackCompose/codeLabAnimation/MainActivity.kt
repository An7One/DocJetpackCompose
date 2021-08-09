package com.an7one.officialDoc.jetpackCompose.codeLabAnimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.an7one.officialDoc.jetpackCompose.codeLabAnimation.ui.page.Home
import com.an7one.officialDoc.jetpackCompose.codeLabAnimation.ui.theme.ThemeCodeLabAnimation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThemeCodeLabAnimation {
                Home()
            }
        }
    }
}