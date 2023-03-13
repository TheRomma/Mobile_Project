package com.jkoivist.mobilecomputingproject.ui.location

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.jkoivist.mobilecomputingproject.MainViewModel

@Composable
fun LocationPicker(
    navController: NavController,
    viewModel: MainViewModel
){
    //val location = LatLng(65.006, 25.441)
    val location = LatLng(viewModel.posY, viewModel.posX)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    val locationMarkerState = rememberMarkerState(
        key = "location",
        position = location
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = locationMarkerState,
                title = "location",
                snippet = "Marker in location",
                draggable = true
            )
            Circle(
                center = locationMarkerState.position,
                strokeColor = Color.Blue,
                radius = 1000.0
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Button(
                onClick = {
                    viewModel.useVirtualLocation = false
                    navController.navigate("home")
                },
                modifier = Modifier
                    .height(80.dp)
                    .weight(0.5f)
            ) {
                Text("Use real location.")
            }
            Button(
                onClick = {
                    viewModel.useVirtualLocation = true
                    viewModel.virtualX = locationMarkerState.position.longitude
                    viewModel.virtualY = locationMarkerState.position.latitude
                    navController.navigate("home")
                },
                modifier = Modifier
                    .height(80.dp)
                    .weight(0.5f)
            ) {
                Text("Set & use virtual location.")
            }
        }
    }
}
