package com.jkoivist.mobile_project.ui.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkoivist.mobile_project.ui.account.AccountActivity

class ReminderItem(val title:String, val message:String)

@Composable
fun Home(){
    val context = LocalContext.current
    val act = context as? Activity

    var logoutDialog by remember { mutableStateOf(false) }

    BackHandler{
        logoutDialog = true
    }
    
    if(logoutDialog){
        AlertDialog(
            onDismissRequest = {logoutDialog = false},
            title = {Text(text = "Logout")},
            text = {Text(text = "Are you sure you want to logout?")},
            buttons = {
                Row(Modifier.padding(10.dp)){
                    Button(onClick = {
                        logoutDialog = false
                        act?.finish()
                    },Modifier
                        .weight(0.5f)
                        .height(80.dp)
                    ){
                        Text(text = "Yes")
                    }
                    Button(onClick = {
                        logoutDialog = false
                    },Modifier
                        .weight(0.5f)
                        .height(80.dp)
                    ){
                        Text(text = "No")
                    }
                }
            }
        )
    }

    val list = listOf(
        ReminderItem("title 1", "message of reminder 1"),
        ReminderItem("title 2", "message of reminder 2"),
        ReminderItem("title 3", "message of reminder 3"),
    )
    Column() {
        LazyColumn(
            Modifier
                .fillMaxHeight(0.8f)
                .padding(20.dp)
        ) {
            items(items = list, itemContent = { item ->
                Button(
                    onClick = {

                    },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                ) {
                    Text(
                        text = item.title,
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Left
                    )
                }
                Spacer(Modifier.height(5.dp))
            })
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Button(onClick = {
                logoutDialog = true
            },
                Modifier
                    .weight(0.33f)
                    .height(80.dp)) {
                Text(text = "Logout")
            }
            Button(onClick = {

            },
                Modifier
                    .weight(0.33f)
                    .height(80.dp)) {
                Text(text = "Add entry")
            }
            Button(onClick = {
                context.startActivity(Intent(context, AccountActivity::class.java))
            },
                Modifier
                    .weight(0.33f)
                    .height(80.dp)) {
                Text(text = "Account settings")
            }
        }
    }
}