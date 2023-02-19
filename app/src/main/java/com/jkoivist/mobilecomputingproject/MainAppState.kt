package com.jkoivist.mobilecomputingproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainAppState(
    val navController: NavHostController
){
    fun navigateBack(){
        navController.popBackStack()
    }
}

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController){
    MainAppState(navController)
}