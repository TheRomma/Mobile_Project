package com.jkoivist.mobilecomputingproject.ui.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jkoivist.mobilecomputingproject.MainViewModel
import com.jkoivist.mobilecomputingproject.data.Reminder

@Composable
fun Edit(
    navController: NavController,
    viewModel: MainViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        val reminder = viewModel.displayReminder
        val title = remember { mutableStateOf(reminder.title) }
        val message = remember { mutableStateOf(reminder.message) }

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title.value.orEmpty(),
                onValueChange = {text -> title.value = text},
                label = { Text(text = "Title") },
                shape = RoundedCornerShape(corner = CornerSize(10.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(160.dp),
                value = message.value.orEmpty(),
                onValueChange = {text -> message.value = text},
                label = { Text(text = "Message") },
                shape = RoundedCornerShape(corner = CornerSize(10.dp))
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("display")
                    },
                    modifier = Modifier.weight(0.5f).height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        var newReminder = Reminder(
                            title.value,
                            message.value,
                            reminder.location_X,
                            reminder.location_Y,
                            reminder.reminder_time,
                            reminder.creation_time,
                            reminder.creator_id,
                            reminder.use_location
                        )
                        newReminder.itemId = reminder.itemId
                        viewModel.displayReminder = newReminder

                        viewModel.updateReminder(
                            newReminder
                        )
                        navController.navigate("display")
                    },
                    modifier = Modifier.weight(0.5f).height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                ) {
                    Text(text = "Save & Exit")
                }
            }
        }
    }
}