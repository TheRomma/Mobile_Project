package com.jkoivist.mobilecomputingproject.ui.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jkoivist.mobilecomputingproject.MainViewModel
import com.jkoivist.mobilecomputingproject.data.Reminder
import java.util.*

@Composable
fun Add(
    navController: NavController,
    viewModel: MainViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ){

        val title = remember { mutableStateOf("") }
        val message = remember { mutableStateOf("") }
        val context = LocalContext.current
        val calendar = Calendar.getInstance()

        var year = calendar[Calendar.YEAR]
        var month = calendar[Calendar.MONTH]
        var day = calendar[Calendar.DAY_OF_MONTH]
        var hour = calendar[Calendar.HOUR_OF_DAY]
        var minute = calendar[Calendar.MINUTE]

        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay : Int ->
                year = selectedYear
                month = selectedMonth
                day = selectedDay
            }, year, month, day
        )

        val timePicker = TimePickerDialog(
            context,
            {_, selectedHour: Int, selectedMinute: Int ->
                hour = selectedHour
                minute = selectedMinute
            }, hour, minute, true
        )

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

            Button(
                onClick = {
                    datePicker.show()
                },modifier = Modifier.fillMaxWidth().height(40.dp)
            ){
                Text("Choose date.")
            }

            Button(
                onClick = {
                    timePicker.show()
                },modifier = Modifier.fillMaxWidth().height(40.dp)
            ){
                Text("Choose time.")
            }

            Button(
                onClick = {
                    navController.navigate("map")
                },modifier = Modifier.fillMaxWidth().height(40.dp)
            ){
                Text("Choose location.")
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("home")
                    },
                    modifier = Modifier.weight(0.5f).height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                ) {
                    Text(text = "Cancel")
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val reminderDateTime = Calendar.getInstance()
                            reminderDateTime.set(year, month, day, hour, minute)

                            val nowDateTime = Calendar.getInstance()

                            val delay =
                                (reminderDateTime.timeInMillis / 1000L) - (nowDateTime.timeInMillis / 1000L)

                            //viewModel.timedNotification(1, title.value, message.value, delay)

                            viewModel.insertReminder(
                                Reminder(
                                    title.value,
                                    message.value,
                                    viewModel.pickedX,
                                    viewModel.pickedY,
                                    0,
                                    nowDateTime.timeInMillis / 1000L,
                                    0,
                                    false
                                )
                            )
                            navController.navigate("home")
                        },
                        modifier = Modifier.weight(0.25f).height(100.dp),
                        shape = RoundedCornerShape(corner = CornerSize(10.dp))
                    ) {
                        Text(text = "Create with no time or location")
                    }
                    Button(
                        onClick = {
                            val reminderDateTime = Calendar.getInstance()
                            reminderDateTime.set(year, month, day, hour, minute)

                            val nowDateTime = Calendar.getInstance()

                            val delay =
                                (reminderDateTime.timeInMillis / 1000L) - (nowDateTime.timeInMillis / 1000L)

                            viewModel.timedNotification(1, title.value, message.value, delay)

                            viewModel.insertReminder(
                                Reminder(
                                    title.value,
                                    message.value,
                                    viewModel.pickedX,
                                    viewModel.pickedY,
                                    reminderDateTime.timeInMillis / 1000L,
                                    nowDateTime.timeInMillis / 1000L,
                                    0,
                                    false
                                )
                            )
                            navController.navigate("home")
                        },
                        modifier = Modifier.weight(0.25f).height(100.dp),
                        shape = RoundedCornerShape(corner = CornerSize(10.dp))
                    ) {
                        Text(text = "Create with timed notification")
                    }
                    Button(
                        onClick = {
                            val reminderDateTime = Calendar.getInstance()
                            reminderDateTime.set(year, month, day, hour, minute)

                            val nowDateTime = Calendar.getInstance()

                            val delay =
                                (reminderDateTime.timeInMillis / 1000L) - (nowDateTime.timeInMillis / 1000L)

                            //viewModel.timedNotification(1, title.value, message.value, delay)

                            viewModel.insertReminder(
                                Reminder(
                                    title.value,
                                    message.value,
                                    viewModel.pickedX,
                                    viewModel.pickedY,
                                    0,
                                    nowDateTime.timeInMillis / 1000L,
                                    0,
                                    true
                                )
                            )
                            navController.navigate("home")
                        },
                        modifier = Modifier.weight(0.25f).height(100.dp),
                        shape = RoundedCornerShape(corner = CornerSize(10.dp))
                    ) {
                        Text(text = "Create with location")
                    }

                    Button(
                        onClick = {
                            val reminderDateTime = Calendar.getInstance()
                            reminderDateTime.set(year, month, day, hour, minute)

                            val nowDateTime = Calendar.getInstance()

                            val delay =
                                (reminderDateTime.timeInMillis / 1000L) - (nowDateTime.timeInMillis / 1000L)

                            viewModel.timedNotification(1, title.value, message.value, delay)

                            viewModel.insertReminder(
                                Reminder(
                                    title.value,
                                    message.value,
                                    viewModel.pickedX,
                                    viewModel.pickedY,
                                    reminderDateTime.timeInMillis / 1000L,
                                    nowDateTime.timeInMillis / 1000L,
                                    0,
                                    true
                                )
                            )
                            navController.navigate("home")
                        },
                        modifier = Modifier.weight(0.25f).height(100.dp),
                        shape = RoundedCornerShape(corner = CornerSize(10.dp))
                    ) {
                        Text(text = "Create with location and time")
                    }

                }
            }
        }
    }
}