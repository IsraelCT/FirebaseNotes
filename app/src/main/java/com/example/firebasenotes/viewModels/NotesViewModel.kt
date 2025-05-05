package com.example.firebasenotes.viewModels

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.model.NotesState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class NotesViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

     private val _notesData  = MutableStateFlow<List<NotesState>>(emptyList())
    val notesData : StateFlow<List<NotesState>> = _notesData.asStateFlow()

        var state by mutableStateOf(NotesState())
            private set
    fun onValue(value : String, text : String){
        when(text){
            "title" -> state = state.copy(title = value)
            "note" -> state = state.copy(note = value)
        }
    }



    fun fetchNotes(){
        val email =auth.currentUser?.email
        firestore.collection("Notes")
            .whereEqualTo("emailUser",email.toString())
            .addSnapshotListener{ querySnapshot , error ->
                if (error!= null){
                    return@addSnapshotListener

                }
                val documents =mutableListOf<NotesState>()
                if(querySnapshot != null){
                    for(document in querySnapshot){
                        val myDocument = document.toObject(NotesState :: class.java).copy(idDoc = document.id)
                        documents.add(myDocument)
                    }
                }
                _notesData.value = documents
            }
    }


    fun saveNewNote(title: String, note: String, onSuccess: () -> Unit) {
        val email = auth.currentUser?.email
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newNote = hashMapOf(
                    "title" to title,
                    "note " to note,
                    "date" to formatDate(),
                    "emailUser" to email.toString()

                )
                firestore.collection("Notes")
                    .add(newNote)
                    .addOnSuccessListener  {
                    onSuccess()
                } //Nota: "El problema que tenia es que no podia guardar mi nota
                // Porque tenia dos {} despues del .addOnSuccessListenerp
            } catch (e: Exception) {
                Log.d("Error save", "Error al guardr ${e.localizedMessage}")
            }
        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun formatDate(): String {
        val currentDate : Date = Calendar.getInstance().time
        val res = SimpleDateFormat(/* pattern = */ "dd/MM/yyyy", /* locale = */ java.util.Locale.getDefault())
        return res.format(currentDate)
    }

    fun getNoteById(documentId : String){
        firestore.collection("Notes")
            .document(documentId)
            .addSnapshotListener { snapshot , _ ->
                if (snapshot != null){
                    val note = snapshot.toObject(NotesState :: class.java)
                    state = state.copy(
                        title = note?.title ?: "",
                        note = note?.note ?: ""
                    )
                }
            }
    }

    fun updateNote(idDoc : String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val editNote = hashMapOf(
                    "title" to state.title,
                    "note " to state.note,

                )
                firestore.collection("Notes").document(idDoc)
                    .update(editNote as Map<String, Any>)
                    .addOnSuccessListener  {
                        onSuccess()
                    } //Nota: "El problema que tenia es que no podia guardar mi nota
                // Porque tenia dos {} despues del .addOnSuccessListenerp
            } catch (e: Exception) {
                Log.d("Error save", "Error al editar ${e.localizedMessage}")
            }
        }

    }

    fun deleteNote(idDoc : String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                firestore.collection("Notes").document(idDoc)
                    .delete()
                    .addOnSuccessListener  {
                        onSuccess()
                    } //Nota: "El problema que tenia es que no podia guardar mi nota
                // Porque tenia dos {} despues del .addOnSuccessListenerp
            } catch (e: Exception) {
                Log.d("Error save", "Error al eliminar ${e.localizedMessage}")
            }
        }

    }

    fun signOut() {
        auth.signOut()
    }
}