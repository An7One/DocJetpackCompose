package com.an7one.officialDoc.jetpackCompose.codeLabNavigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class RallyNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyNavHost() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            RallyNavHost(navController = navController)
        }
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }
}