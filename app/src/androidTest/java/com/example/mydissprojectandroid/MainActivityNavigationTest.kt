package com.example.mydissprojectandroid

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
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
class MainActivityNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.BLUETOOTH,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
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
        Thread.sleep(5000)
        assert(navController.currentBackStackEntry?.destination?.route == "MediaScreen")
    }

    @Test
    fun testNavigationToCameraScreen() {
        composeTestRule.onNodeWithText("Camera").performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
        assert(navController.currentBackStackEntry?.destination?.route == "CameraScreen")
    }

    @Test
    fun testNavigationToFileScreen() {
        composeTestRule.onNodeWithText("File").performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
        assert(navController.currentBackStackEntry?.destination?.route == "FileScreen")
    }

    @Test
    fun testNavigationToMicrophoneScreen() {
        composeTestRule.onNodeWithText("Microphone").performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
        assert(navController.currentBackStackEntry?.destination?.route == "MicroScreen")
    }

    @Test
    fun testNavigationToBluetoothScreen() {
        composeTestRule.onNodeWithText("Bluetooth").performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
        assert(navController.currentBackStackEntry?.destination?.route == "BluetoothScreen")
    }

    @Test
    fun testNavigationToLocationScreen() {
        composeTestRule.onNodeWithText("Location").performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
        assert(navController.currentBackStackEntry?.destination?.route == "LocationScreen")
    }
}