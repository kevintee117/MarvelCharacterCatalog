package com.marvelcharactercatalog

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.marvelcharactercatalog.activities.MainActivity
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testComicDisplayed() {

        // Check if the comic name is displayed
        composeTestRule.onNodeWithText("No Comic Name found").assertExists()
        // Check if the "The Story" is displayed
        composeTestRule.onNodeWithText("The Story").assertExists()
        // Check if the No description with attribution is displayed correctly
        composeTestRule.onNodeWithText("No Description found\n\nData provided by Marvel. © 2014 Marvel").assertExists()

    }

    @Test
    fun testButtonVisibility() {

        // Check if the "Mark as Read" button is displayed
        composeTestRule.onNodeWithText("Mark as Read").assertExists()

        // Check if the "Add to Library" button is displayed
        composeTestRule.onNodeWithText("Add to library").assertExists()

        // Check if the "Read Offline" button is displayed
        composeTestRule.onNodeWithText("Read offline").assertExists()
    }

}
