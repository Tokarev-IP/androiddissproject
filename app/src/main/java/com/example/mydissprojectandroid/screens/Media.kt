package com.example.mydissprojectandroid.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mydissprojectandroid.common.saveSquareToGallery

@Composable
fun MediaComposeScreen(
    context: Context
) {
    var uriListMedia by rememberSaveable { mutableStateOf<List<Uri>>(emptyList()) }

    val pickMultiMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { newUriList: List<Uri> ->
        uriListMedia = newUriList
    }

    MediaComposeView(
        context = context,
        onPickMultiMedia = {
            pickMultiMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        pickedMultiMedia = uriListMedia,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaComposeView(
    modifier: Modifier = Modifier,
    context: Context,
    onPickMultiMedia: () -> Unit,
    pickedMultiMedia: List<Uri?>,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onPickMultiMedia()
                })
            {
                Icon(Icons.Filled.AddCircle, contentDescription = "Take a Photo")
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                if (pickedMultiMedia.isEmpty())
                    Box(modifier = modifier.fillMaxSize()) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(600.dp)
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "No images are selected",
                            )
                            Spacer(modifier = modifier.height(12.dp))
                            OutlinedButton(onClick = {
                                saveSquareToGallery(context = context)
                            }) {
                                Text(text = "Save a sample JPEG")
                            }
                        }
                    }
            }

            items(pickedMultiMedia.size) {
                if (pickedMultiMedia[it] != null) {
                    AsyncImage(
                        model = pickedMultiMedia[it],
                        contentDescription = "Photo"
                    )
                    Spacer(modifier = modifier.height(12.dp))
                }
            }
        }
    }
}