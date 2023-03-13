package com.jkoivist.mobilecomputingproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jkoivist.mobilecomputingproject.ui.map.MapPicker
import com.jkoivist.mobilecomputingproject.ui.account.Account
import com.jkoivist.mobilecomputingproject.ui.add.Add
import com.jkoivist.mobilecomputingproject.ui.display.Display
import com.jkoivist.mobilecomputingproject.ui.edit.Edit
import com.jkoivist.mobilecomputingproject.ui.home.Home
import com.jkoivist.mobilecomputingproject.ui.location.LocationPicker
import com.jkoivist.mobilecomputingproject.ui.login.Login

@Composable
fun MainApp(
    appState: MainAppState = rememberMainAppState(),
    viewModel: MainViewModel
){
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ){
        composable(route = "login"){
            Login(navController = appState.navController)
        }
        composable(route = "home"){
            Home(
                navController = appState.navController,
                viewModel = viewModel
                )
        }
        composable(route = "account"){
            Account(navController = appState.navController)
        }
        composable(route = "location"){
            LocationPicker(
                navController = appState.navController,
                viewModel = viewModel
            )
        }
        composable(route = "add"){
            Add(
                navController = appState.navController,
                viewModel = viewModel
            )
        }
        composable(route = "map"){
            MapPicker(
                navController = appState.navController,
                viewModel = viewModel
            )
        }
        composable(route = "display"){
            Display(
                navController = appState.navController,
                viewModel = viewModel
            )
        }
        composable(route = "edit"){
            Edit(
                navController = appState.navController,
                viewModel = viewModel
            )
        }
    }
}