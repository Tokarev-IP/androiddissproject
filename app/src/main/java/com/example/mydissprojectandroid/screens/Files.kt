package com.example.mydissprojectandroid.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun FileCompose(context: Context) {

    var readFileUri by rememberSaveable { mutableStateOf<String>("") }
    var savedText = ""

    val getFolderUriLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = { uri: Uri? ->
            uri?.let {
                saveFileWithText(context, uri,"filetextname.txt", savedText)
            }
        }
    )
    val writeStorageUriRequestLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
                getFolderUriLauncher.launch(null)
        }

    val getFileUriToReadLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val text = readTextInFile(context, uri)
                readFileUri = text
            }
        }
    )
    val readStorageRequestLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
                getFileUriToReadLauncher.launch("text/plain")
        }

    FilesComposeView(
        onReadFile = {
            readStorageRequestLaunch.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        },
        onSaveFile = { text: String ->
            savedText = text
            writeStorageUriRequestLaunch.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        },
        readFileUri = readFileUri.trim(),
    )

}

@Composable
fun FilesComposeView(
    modifier: Modifier = Modifier,
    onReadFile: () -> Unit,
    onSaveFile: (text: String) -> Unit,
    readFileUri: String,
) {
    var textString by rememberSaveable { mutableStateOf("") }

    textString = readFileUri

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues: PaddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = modifier.semantics {
                    contentDescription = "Text field"
                },
                value = textString,
                onValueChange = { text: String ->
                    textString = text
                },
                maxLines = 3,
                supportingText = {
                    Text(text = "Write a text here")
                },
            )
            Spacer(modifier = modifier.height(24.dp))
            OutlinedButton(
                onClick = {
                    onReadFile()
                })
            {
                Text(text = "Read a file")
            }

            Spacer(modifier = modifier.height(24.dp))
            OutlinedButton(
                onClick = {
                    onSaveFile(textString)
                })
            {
                Text(text = "Save in a new file")
            }
        }
    }
}

private fun readTextInFile(context: Context, uri: Uri): String {
    val stringBuilder = StringBuilder()
    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = reader.readLine()
            }
        }
    }
    return stringBuilder.toString()
}

private fun saveFileWithText(
    context: Context,
    folderUri: Uri,
    fileName: String,
    textContent: String
) {
    val folder = DocumentFile.fromTreeUri(context, folderUri)
    val file = folder?.createFile("text/plain", fileName)
    file?.uri?.let { fileUri ->
        context.contentResolver.openOutputStream(fileUri)?.use { outputStream ->
            outputStream.write(textContent.toByteArray())
        }
    }
}