package com.jkoivist.mobilecomputingproject.ui.display

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jkoivist.mobilecomputingproject.MainViewModel
import com.jkoivist.mobilecomputingproject.data.Reminder

@Composable
fun Display(
    navController: NavController,
    viewModel: MainViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        val reminder = viewModel.displayReminder
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            //verticalArrangement = Arrangement.Center
        ){
            Column(
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                Card(modifier = Modifier.fillMaxWidth().height(50.dp).padding(10.dp)) {
                    Text(
                        //modifier = Modifier.padding(8.dp),
                        text = reminder.title,
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Card(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp)) {
                    Text(
                        //modifier = Modifier.padding(8.dp),
                        text = reminder.message,
                        //style = TextStyle(fontSize = 12.sp)
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp))
            {
                Button(onClick = {
                    //logoutDialog = true
                    navController.navigate("home")
                },
                    Modifier
                        .weight(0.33f)
                        .height(80.dp)) {
                    Text(text = "Back")
                }
                Button(onClick = {
                    navController.navigate("edit")
                },
                    Modifier
                        .weight(0.33f)
                        .height(80.dp)) {
                    Text(text = "Edit")
                }
                Button(onClick = {
                    viewModel.deleteReminderById(reminder.itemId)
                    navController.navigate("home")
                },
                    Modifier
                        .weight(0.33f)
                        .height(80.dp)
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}