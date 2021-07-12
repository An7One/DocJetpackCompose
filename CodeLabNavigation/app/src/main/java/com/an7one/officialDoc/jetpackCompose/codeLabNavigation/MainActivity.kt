package com.an7one.officialDoc.jetpackCompose.codeLabNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.RallyScreen
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.RallyTabRow
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.theme.RallyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
private fun RallyApp() {
    RallyTheme {
        val allScreens = RallyScreen.values().toList()
        var curScreen by rememberSaveable { mutableStateOf(RallyScreen.Overview) }
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen -> curScreen == screen },
                    curScreen = curScreen
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                curScreen.content(
                    onScreenChange = { screen ->
                        curScreen = RallyScreen.valueOf(screen)
                    }
                )
            }
        }
    }
}