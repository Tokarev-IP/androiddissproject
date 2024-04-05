package com.example.mydissprojectandroid

import android.Manifest
import android.content.Context
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mydissprojectandroid.screens.BluetoothScreen
import com.example.mydissprojectandroid.screens.CameraComposableScreen
import com.example.mydissprojectandroid.screens.FilesCompose
import com.example.mydissprojectandroid.screens.LocationScreen
import com.example.mydissprojectandroid.screens.MediaCompose
import com.example.mydissprojectandroid.screens.MicrophoneScreen

@Composable
fun MainActivityCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "InitialScreen",
    context: Context,
) {
    val microRequestLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                navController.navigate("MicroScreen")
            }
        }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues: PaddingValues ->

        NavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(route = "InitialScreen") {
                InitialScreen(
                    goToMedia = {
                        navController.navigate("MediaScreen")
                    },
                    goToFile = {
                        navController.navigate("FileScreen")
                    },
                    goToMicro = {
                        microRequestLaunch.launch(Manifest.permission.RECORD_AUDIO)
                    },
                    goToBluetooth = {
                        navController.navigate("BluetoothScreen")
                    },
                    goToCamera = {
                        navController.navigate("CameraScreen")
                    },
                    goToLocation = {
                        navController.navigate("LocationScreen")
                    }
                )
            }
            composable(route = "MediaScreen") {
                MediaCompose()
            }
            composable(route = "FileScreen") {
                FilesCompose(context = context)
            }
            composable(route = "CameraScreen") {
                CameraComposableScreen(context = context)
            }
            composable(route = "BluetoothScreen") {
                BluetoothScreen(context = context)
            }
            composable(route = "MicroScreen") {
                MicrophoneScreen(context = context)
            }
            composable(route = "LocationScreen") {
                LocationScreen(context = context)
            }
        }
    }
}

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    goToMedia: () -> Unit,
    goToFile: () -> Unit,
    goToMicro: () -> Unit,
    goToBluetooth: () -> Unit,
    goToCamera: () -> Unit,
    goToLocation:() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedButton(onClick = { goToMedia() }) {
            Text(text = "Media")
        }
        Spacer(modifier = modifier.height(12.dp))

        OutlinedButton(onClick = { goToCamera() }) {
            Text(text = "Camera")
        }
        Spacer(modifier = modifier.height(12.dp))

        OutlinedButton(onClick = { goToFile() }) {
            Text(text = "File")
        }
        Spacer(modifier = modifier.height(12.dp))

        OutlinedButton(onClick = { goToMicro() }) {
            Text(text = "Micro")
        }
        Spacer(modifier = modifier.height(12.dp))

        OutlinedButton(onClick = { goToBluetooth() }) {
            Text(text = "Bluetooth")
        }
        Spacer(modifier = modifier.height(12.dp))

        OutlinedButton(onClick = { goToLocation() }) {
            Text(text = "Location")
        }
        Spacer(modifier = modifier.height(12.dp))
    }
}