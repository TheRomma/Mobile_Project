package com.jkoivist.mobile_project.ui.account

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
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

@Composable
fun AccountSettings(){
    val context = LocalContext.current
    val act = context as? Activity
    val prefs = context.getSharedPreferences("prefs.xml", Context.MODE_PRIVATE)

    val username = remember { mutableStateOf(value= prefs.getString("username", "defaultuser")) }
    val password = remember { mutableStateOf(value= prefs.getString("password", "defaultpassword")) }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){

        Text(
            text = "Change username & password.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username.value.orEmpty(),
            onValueChange = {text -> username.value = text},
            label = { Text(text = "Username") },
            shape = RoundedCornerShape(corner = CornerSize(10.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value.orEmpty(),
            onValueChange = {text -> password.value = text},
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(corner = CornerSize(10.dp))
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                var editor = prefs.edit()
                editor.putString("username", username.value)
                editor.putString("password", password.value)
                editor.commit()
                act?.finish()
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp))
        ){
            Text(text = "Save and exit")
        }
    }
}