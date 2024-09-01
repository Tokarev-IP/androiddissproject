package com.example.mydissprojectandroid.screens

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat

@Composable
fun BluetoothScreen(
    modifier: Modifier = Modifier,
    context: Context,
) {
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    var isEnabled by remember { mutableStateOf(bluetoothAdapter?.isEnabled ?: false) }

    val bluetoothRequestLaunch =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
                if (bluetoothAdapter?.isEnabled == true) {
                    val isDisable: Boolean = bluetoothAdapter.disable()
                    isEnabled = !isDisable
                } else if ((bluetoothAdapter?.isEnabled == false)) {
                    isEnabled = bluetoothAdapter.enable()
                }
        }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(text = "Push the button to turn on/off bluetooth")
        Spacer(modifier = modifier.height(12.dp))
        Button(
            onClick = {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    bluetoothRequestLaunch.launch(Manifest.permission.BLUETOOTH_CONNECT)
                }
                bluetoothRequestLaunch.launch(Manifest.permission.BLUETOOTH_CONNECT)
            },
        ) {
            Text(text = "Turn on/off the Bluetooth")
        }
        Spacer(modifier = modifier.height(12.dp))
        Text(text = "Status: Bluetooth is ${if (isEnabled) "ON" else "OFF"}")
    }
}