package com.example.firebasenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebasenotes.viewModels.LoginViewmodel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.example.firebasenotes.views.Login.BlankView
import com.example.firebasenotes.views.Login.TabsView
import com.example.firebasenotes.views.Notes.AddNoteView
import com.example.firebasenotes.views.Notes.EditNoteView
import com.example.firebasenotes.views.Notes.HomeView

@Composable
fun NavManager(loginVM: LoginViewmodel,notesVM: NotesViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Blank"){
        composable("Blank"){
            BlankView(navController)

        }
        composable("Login"){
            TabsView(navController,loginVM)

        }
        composable ("Home"){
            HomeView(navController,notesVM)
        }
        composable ("AddNoteView"){
            AddNoteView(navController,notesVM)
        }
        composable ("EditNoteView/{idDoc}", arguments = listOf(
            navArgument ("idDoc"){
                type = NavType.StringType
            }
        )){
            val idDoc = it.arguments?.getString("idDoc") ?:""
            EditNoteView(navController,notesVM, idDoc )
        }
    }
}