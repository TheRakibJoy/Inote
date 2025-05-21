package com.example.inote


import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.PrimaryKey
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*


data class Note(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var content: String = ""
)

class NoteViewModel : ViewModel() {
    private val _notes = mutableStateListOf<Note>()
    var searchedText: MutableState<String> = mutableStateOf("")
    var selectedNoteIds by mutableStateOf(setOf<String>())
    val notes: List<Note>
        get() = if(searchedText.value==""){
            _notes
        }
        else{
            _notes.filter {
                it.title.contains(searchedText.value, ignoreCase = true) ||
                        it.content.contains(searchedText.value, ignoreCase = true)
            }
        }

    fun addNote(note: Note) {
        _notes.add(note)
    }
    fun getNoteById(id: String): Note? {
        return notes.find { it.id == id }
    }
    fun updateNote(note: Note){
        val index = _notes.indexOfFirst{it.id==note.id}
        if(index != -1){
            _notes[index] = note
        }
    }
    fun deleteNote(id: String){
        _notes.removeIf{it.id==id}
    }
    fun toggleNotesSelection(noteId: String){
        selectedNoteIds = selectedNoteIds.let {
            if (it.contains(noteId)) it - noteId else it+noteId
        }
    }
    fun isNoteSelected(noteId: String): Boolean{
        return selectedNoteIds.contains(noteId)
    }
    fun clearSelection(){
        selectedNoteIds = emptySet()
    }
    fun isAnySelected():Boolean{
        return selectedNoteIds.isNotEmpty()
    }
    fun deleteSelected(){
      selectedNoteIds.forEach{noteId->
            deleteNote(id = noteId)
        }
        clearSelection()
    }
}