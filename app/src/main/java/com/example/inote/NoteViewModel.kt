package com.example.inote


import android.app.Application
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
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


@Serializable
data class Note(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var content: String = ""
)

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val notesFile = File(application.filesDir,"notes.json")
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

    init {
        loadNotes()
    }
    fun addNote(note: Note) {
        _notes.add(note)
        saveNotes()
    }
    fun getNoteById(id: String): Note? {
        return notes.find { it.id == id }
    }
    fun updateNote(note: Note){
        val index = _notes.indexOfFirst{it.id==note.id}
        if(index != -1){
            _notes[index] = note
            saveNotes()
        }
    }
    fun deleteNote(id: String){
        _notes.removeIf{it.id==id}
        saveNotes()
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


    private fun loadNotes(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                if(notesFile.exists()){
                    val json = notesFile.readText()
                    val list = Json.decodeFromString<List<Note>>(json)
                    withContext(Dispatchers.Main){
                        _notes.clear()
                        _notes.addAll(list)
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    private fun saveNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jsonString = Json.encodeToString(_notes.toList())
                notesFile.writeText(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}