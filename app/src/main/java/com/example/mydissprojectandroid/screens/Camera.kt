package com.example.mydissprojectandroid.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CameraComposableScreen(
    modifier: Modifier = Modifier,
    context: Context,
) {
    var photoBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    val takePhotoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            photoBitmap = bitmap
        }

    val cameraRequestLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
                takePhotoLauncher.launch(null)
        }

    LazyColumn(
        modifier = modifier.padding(horizontal = 12.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item() {
            Spacer(modifier = modifier.height(12.dp))
            Button(onClick = {
                cameraRequestLaunch.launch(Manifest.permission.CAMERA)
            }) {
                Text(text = "Take a photo")
            }
            Spacer(modifier = modifier.height(24.dp))
            AsyncImage(
                model = photoBitmap,
                contentDescription = "Photo",
                modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}
