package com.example.firebasenotes.views.Notes

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // <-- Importa la función items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue

import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items //
import androidx.compose.material3.Card
import com.example.firebasenotes.components.CardNote
import com.example.firebasenotes.model.NotesState


import com.example.firebasenotes.viewModels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(navController: NavController, notesVM: NotesViewModel) {
    LaunchedEffect(Unit) {
        notesVM.fetchNotes()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mis notas") },
                navigationIcon = {
                    IconButton(onClick = {
                        notesVM.signOut()
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("AddNoteView")
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    }

                }
            )
        }
    ){
        pad ->
        Column (
            modifier = Modifier.padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val datos by notesVM.notesData.collectAsState()
            LazyColumn {
                items (datos) {
                    item ->
                    CardNote(title =item.title, note = item.note, date = item.date ) { }
                    navController.navigate("EditNoteView/${item.idDoc}")
                }
            }
            }
        }
    }

