package com.example.mydissprojectandroid.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MicrophoneScreen(
    modifier: Modifier = Modifier,
    context: Context,
) {
    var isRecording by remember { mutableStateOf(false) }
    var amplitude by remember { mutableStateOf(0) }

    val microRequestLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                isRecording = !isRecording
            }
        }

    Column(
        modifier = modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Button(
            onClick = {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    isRecording = !isRecording
                } else {
                    microRequestLaunch.launch(Manifest.permission.RECORD_AUDIO)
                }
            }) {
            Text(if (isRecording) "Stop" else "Record")
        }
        Text(text = "Amplitude: $amplitude")
    }

    val audioRecord by remember {
        mutableStateOf(
            AudioRecord.Builder()
                .setAudioSource(MediaRecorder.AudioSource.MIC)
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(44100)
                        .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                        .build()
                )
                .setBufferSizeInBytes(
                    AudioRecord.getMinBufferSize(
                        44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT
                    )
                )
                .build()
        )
    }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            audioRecord.startRecording()
            val buffer = ShortArray(1024)
            while (isRecording) {
                val readResult = audioRecord.read(buffer, 0, buffer.size)
                if (readResult > 0) {
                    withContext(Dispatchers.Main) {
                        amplitude = buffer.maxOrNull()?.toInt() ?: 0
                    }
                }
            }
        } else {
            audioRecord.stop()
        }
    }
}