package com.example.mydissprojectandroid

import android.content.Context
import android.net.Uri
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mydissprojectandroid.common.readFile
import com.example.mydissprojectandroid.common.saveText
import com.example.mydissprojectandroid.screens.FilesComposeView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FileComposableScreenTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test() {

        val fileUri: Uri? = saveText(
            context,
            "This is a test text for testing"
        )

        var text = ""

        fileUri?.let { it ->
            text = readFile(context, it)
        }

        composeTestRule.setContent {
            FilesComposeView(
                onReadFile = {},
                onSaveFile = {},
                readFileUri = text.trim(),
            )
        }

        composeTestRule.waitForIdle()

        Thread.sleep(5000)

        assert(text.trim() == "This is a test text for testing")

    }

}