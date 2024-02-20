package com.marvelcharactercatalog

import androidx.activity.ComponentActivity
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
        composeTestRule.onNodeWithText("Spider-Man (2022) #11").assertExists()

        // Check if the comic description is displayed
        composeTestRule.onNodeWithText("(RE)INTRODUCING…SPIDER-BOY! The battle to save the Spider-Verse may be over, but spinning out of the restored Web of Life and Destiny returns the spectacular SPIDER-BOY, Peter Parker's stupendous sidekick! Wait, that can't be right - who IS this Spider-Boy, and what is his connection to the Amazing Spider-Man?!").assertExists()
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
