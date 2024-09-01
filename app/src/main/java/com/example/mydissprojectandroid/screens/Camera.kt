package com.example.mydissprojectandroid.screens

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun CameraComposableScreen() {
    var photoBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    val takePhotoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            photoBitmap = bitmap
        }

    CameraComposableView(
        onPhotoTaken = {
            takePhotoLauncher.launch(null)
        },
        takenPhoto = photoBitmap
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraComposableView(
    modifier: Modifier = Modifier,
    onPhotoTaken: () -> Unit,
    takenPhoto: Bitmap?,
) {

    val cameraRequestLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                onPhotoTaken()
            }
        }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { "Camera" })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { cameraRequestLaunch.launch(Manifest.permission.CAMERA) }) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Take a picture")
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (takenPhoto != null)
                AsyncImage(
                    model = takenPhoto,
                    contentDescription = "Photo",
                    modifier = modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            else {
                Box(
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = modifier.align(Alignment.Center),
                        text = "No image is taken",
                    )
                }
            }

        }
    }
}