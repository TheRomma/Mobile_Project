package com.jkoivist.mobilecomputingproject.ui.add

import android.content.Context
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
                        navController.navigate("home")
                    },
                    modifier = Modifier.weight(0.5f).height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        viewModel.insertReminder(
                            Reminder(
                                title.value,
                                message.value,
                                0,
                                0,
                                0,
                                0,
                                0
                            )
                        )
                        navController.navigate("home")
                    },
                    modifier = Modifier.weight(0.5f).height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                ) {
                    Text(text = "Create reminder")
                }
            }
        }
    }
}