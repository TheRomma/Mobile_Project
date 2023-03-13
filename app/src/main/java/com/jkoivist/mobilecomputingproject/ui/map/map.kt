package com.jkoivist.mobilecomputingproject.ui.map

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.jkoivist.mobilecomputingproject.MainViewModel

@Composable
fun MapPicker(
    navController: NavController,
    viewModel: MainViewModel
){
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
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = locationMarkerState,
                title = "location",
                snippet = "Marker in location",
                draggable = true
            )
        }
        Button(
            onClick = {
                viewModel.pickedX = locationMarkerState.position.longitude
                viewModel.pickedY = locationMarkerState.position.latitude
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth().height(80.dp)
        ){
            Text("Set location.")
        }
    }
}
