package com.jkoivist.mobilecomputingproject.ui.home

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.jkoivist.mobilecomputingproject.MainViewModel
import com.jkoivist.mobilecomputingproject.data.Reminder
import java.util.*

class SimpleRem(val title: String, val message: String)

@Composable
fun Home(
    navController: NavController,
    viewModel: MainViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        val allReminders by viewModel.allReminders.observeAsState(listOf())
        val nowDateTime = Calendar.getInstance().timeInMillis/1000L

        var locA: Location = Location("A")
        var locB: Location = Location("B")
        locA.longitude = viewModel.virtualX
        locA.latitude = viewModel.virtualY

        Column() {
            LazyColumn(
                Modifier
                    .fillMaxHeight(0.8f)
                    .padding(20.dp),
                reverseLayout = true
            ) {
                if(!viewModel.useVirtualLocation){
                    locA.longitude = viewModel.posX
                    locA.latitude = viewModel.posY
                }else{
                    locA.longitude = viewModel.virtualX
                    locA.latitude = viewModel.virtualY
                }
                items(items = allReminders, itemContent = { item ->
                    locB.longitude = item.location_X
                    locB.latitude = item.location_Y
                    if(/*(item.reminder_time < nowDateTime) || */!item.use_location || item.use_location && (1000.0 > locA.distanceTo(locB))) {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            shape = RoundedCornerShape(corner = CornerSize(10.dp))
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                /*
                            Icon(
                                painter = rememberVectorPainter(Icons.Filled.Person),
                                contentDescription = "entry_image",
                                modifier = Modifier.size(60.dp),
                            )

                             */
                                Text(
                                    text = item.title,
                                    style = TextStyle(fontSize = 20.sp),
                                    textAlign = TextAlign.Left
                                )
                                Spacer(Modifier.weight(1f))
                                OutlinedButton(
                                    onClick = {
                                        //viewModel.getReminderById(item.itemId)
                                        viewModel.displayReminder = item
                                        navController.navigate("display")
                                    },
                                    shape = CircleShape
                                ) {
                                    Icon(
                                        painter = rememberVectorPainter(Icons.Filled.Edit),
                                        contentDescription = "edit_image",
                                        modifier = Modifier.size(30.dp),
                                    )
                                }
                            }


                        }
                    }
                    Spacer(Modifier.height(5.dp))
                })
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)) {
                Button(onClick = {
                    //logoutDialog = true
                    navController.navigate("login")
                },
                    Modifier
                        .weight(0.25f)
                        .height(80.dp)) {
                    Text(text = "Logout")
                }
                Button(
                    onClick = {
                        navController.navigate("location")
                    },modifier = Modifier
                        .weight(0.25f).height(80.dp)
                ){
                    Text("Location")
                }
                Button(onClick = {
                    navController.navigate("add")
                },
                    Modifier
                        .weight(0.25f)
                        .height(80.dp)) {
                    Text(text = "Add entry")
                }
                Button(onClick = {
                    //context.startActivity(Intent(context, AccountActivity::class.java))
                    navController.navigate("account")
                },
                    Modifier
                        .weight(0.25f)
                        .height(80.dp)
                ) {
                    Text(text = "Account settings")
                }
            }
        }
    }
}