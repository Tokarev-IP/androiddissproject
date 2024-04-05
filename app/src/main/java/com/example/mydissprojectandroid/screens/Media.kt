package com.example.mydissprojectandroid.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MediaCompose(
    modifier: Modifier = Modifier,
) {
    var uriListMedia by rememberSaveable { mutableStateOf<List<Uri>?>(null) }
    val pickMultiMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { newUriList: List<Uri> ->
        uriListMedia = newUriList
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = modifier.height(12.dp))
            Button(
                onClick = {
                    pickMultiMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                })
            {
                Text(text = "Pick multiple images")
            }
            Button(
                onClick = {
                    pickMultiMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                })
            {
                Text(text = "Pick list of videos")
            }
            Spacer(modifier = modifier.height(12.dp))
        }
        uriListMedia?.let { list ->
            items(list.size) {
                AsyncImage(
                    model = list[it],
                    contentDescription = "Photo"
                )
                Spacer(modifier = modifier.height(12.dp))
            }
        }
    }
}
