package com.jkoivist.mobilecomputingproject.ui.login

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Login(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val username = remember { mutableStateOf(value = "") }
        val password = remember { mutableStateOf(value = "") }
        val failText = remember { mutableStateOf(value = "") }

        val context = LocalContext.current
        val prefs = context.getSharedPreferences("prefs.xml", Context.MODE_PRIVATE)

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.Filled.Person),
                contentDescription = "login_image",
                modifier = Modifier.fillMaxWidth().size(150.dp),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = failText.value,
                textAlign = TextAlign.Center,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username.value,
                onValueChange = { text -> username.value = text },
                label = { Text(text = "Username") },
                shape = RoundedCornerShape(corner = CornerSize(10.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password.value,
                onValueChange = { text -> password.value = text },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(corner = CornerSize(10.dp))
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if(username.value == prefs.getString("username", "defaultname") && password.value == prefs.getString("password", "defaultname")){
                        failText.value = ""
                        username.value = ""
                        password.value = ""
                        navController.navigate("home")
                    }else{
                        failText.value = "Username or password wrong!"
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(corner = CornerSize(10.dp))
            ){
                Text("Login")
            }
        }
    }
}
/*
@Preview
@Composable
fun Preview(){
    Login()
}
*/