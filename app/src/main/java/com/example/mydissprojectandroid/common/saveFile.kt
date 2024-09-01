package com.example.mydissprojectandroid.common

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.BufferedReader
import java.io.InputStreamReader

fun readFile(context: Context, uri: Uri): String {
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

fun saveText(
    context: Context,
    textContent: String
): Uri? {
    val fileName = "test_text_file.txt"

    val contentValues = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME, fileName)
        put(MediaStore.Downloads.MIME_TYPE, "text/plain")
        put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.write(textContent.toByteArray())
        }
        return uri
    }
    return null

}