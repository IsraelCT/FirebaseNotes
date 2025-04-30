package com.example.firebasenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotes.viewModels.LoginViewmodel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.example.firebasenotes.views.Login.TabsView
import com.example.firebasenotes.views.Notes.HomeView

@Composable
fun NavManager(loginVM: LoginViewmodel,notesVM: NotesViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login"){
        composable("Login"){
            TabsView(navController,loginVM)

        }
        composable ("Home"){
            HomeView()
        }
    }
}