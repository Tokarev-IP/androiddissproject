package com.example.mydissprojectandroid

import android.content.Context
import android.net.Uri
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.example.mydissprojectandroid.common.saveSquareToGallery
import com.example.mydissprojectandroid.screens.MediaComposeView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaComposableScreenTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testShowChosenMultiMedia() {
        val list = mutableListOf<Uri?>()

        for (i in 1..2) {
            list.add(saveSquareToGallery(context))
        }

        composeTestRule.setContent {
            MediaComposeView(
                context = context,
                onPickMultiMedia = {},
                pickedMultiMedia = list
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithContentDescription("Photo").assertCountEquals(list.size)

        Thread.sleep(5000)
    }
}