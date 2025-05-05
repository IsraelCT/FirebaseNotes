package com.example.firebasenotes.views.Notes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firebasenotes.viewModels.NotesViewModel

@Composable
fun EditNoteView(navController: NavController,noteVM : NotesViewModel, idDoc : String ){
    Text(text = idDoc)
}