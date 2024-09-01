package com.example.mydissprojectandroid

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mydissprojectandroid.screens.CameraComposableView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CameraComposableScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPhotoDisplayedAfterTakingPicture() {
        val testBitmap = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(testBitmap)
        canvas.drawColor(Color.RED)

        composeTestRule.setContent {
            CameraComposableView(
                onPhotoTaken = {},
                takenPhoto = testBitmap
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithContentDescription("Photo").assertCountEquals(1)

        Thread.sleep(5000)
    }
}