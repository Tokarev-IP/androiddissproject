package com.example.mydissprojectandroid

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.example.mydissprojectandroid.ui.theme.MydissprojectandroidTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Tests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.BLUETOOTH,
        android.Manifest.permission.BLUETOOTH_CONNECT,
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.CAMERA,
    )

    private lateinit var navController: TestNavHostController
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setup() {
        composeTestRule.setContent {
            MydissprojectandroidTheme {
                navController = TestNavHostController(context)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                MainActivityCompose(navController = navController, context = ApplicationProvider.getApplicationContext())
            }
        }
    }
    @Test
    fun testNavigationToMediaScreen() {
        composeTestRule.onNodeWithText("Media").performClick()
        composeTestRule.waitForIdle()

        assert(navController.currentBackStackEntry?.destination?.route == "MediaScreen")

        Thread.sleep(3000)

    }

    @Test
    fun testNavigationToCameraScreen() {
        composeTestRule.onNodeWithText("Camera").performClick()
        composeTestRule.waitForIdle()

        assert(navController.currentBackStackEntry?.destination?.route == "CameraScreen")

        Thread.sleep(3000)
    }

    @Test
    fun testNavigationToFileScreen() {
        composeTestRule.onNodeWithText("File").performClick()
        composeTestRule.waitForIdle()

        assert(navController.currentBackStackEntry?.destination?.route == "FileScreen")

        Thread.sleep(3000)
    }

    @Test
    fun testNavigationToMicrophoneScreen() {
        composeTestRule.onNodeWithText("Microphone").performClick()
        composeTestRule.waitForIdle()
        assert(navController.currentBackStackEntry?.destination?.route == "MicroScreen")

        Thread.sleep(3000)

        composeTestRule.onNodeWithText("Record").assertExists()
        composeTestRule.onNodeWithText("Record").performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(10000)

        composeTestRule.onNodeWithText("Stop").assertExists()
        composeTestRule.onNodeWithText("Stop").performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(3000)
    }

    @Test
    fun testNavigationToBluetoothScreen() {
        composeTestRule.onNodeWithText("Bluetooth").performClick()
        composeTestRule.waitForIdle()
        assert(navController.currentBackStackEntry?.destination?.route == "BluetoothScreen")

        Thread.sleep(3000)

        composeTestRule.onNodeWithText("Turn on/off the Bluetooth").assertExists()
        composeTestRule.onNodeWithText("Turn on/off the Bluetooth").performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(7000)

        composeTestRule.onNodeWithText("Turn on/off the Bluetooth").assertExists()
        composeTestRule.onNodeWithText("Turn on/off the Bluetooth").performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(7000)

        composeTestRule.onNodeWithText("Turn on/off the Bluetooth").assertExists()
        composeTestRule.onNodeWithText("Turn on/off the Bluetooth").performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(2000)
    }

    @Test
    fun testNavigationToLocationScreen() {
        composeTestRule.onNodeWithText("Location").performClick()
        composeTestRule.waitForIdle()

        assert(navController.currentBackStackEntry?.destination?.route == "LocationScreen")
        composeTestRule.onNodeWithText("Get location").assertExists()

        Thread.sleep(3000)

        composeTestRule.onNodeWithText("Get location").performClick()
        composeTestRule.onNodeWithContentDescription("Get current location button").performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(10000)

    }
}