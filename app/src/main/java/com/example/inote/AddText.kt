package com.example.inote

import com.example.inote.Note
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.materialIcon
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddText(
    viewModel: NoteViewModel,
    navController:NavHostController,
    editNoteId: String? = null
)
{
    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }
    if (editNoteId != null){
        var note = viewModel.getNoteById(editNoteId)
        if (note != null) {
            title = note.title
        }
        if (note != null) {
            content = note.content
        }
    }
    val handleBackPress = {
        if(editNoteId == null){
            if(title.isNotBlank() or content.isNotBlank()){
                val note = Note(title = title, content = content)
                viewModel.addNote(note)
            }
        }
        else{
            if(title.isNotBlank() or content.isNotBlank()){
                val note = Note(id = editNoteId!!, title = title, content = content)
                viewModel.updateNote(note)
            }
            else{
                viewModel.deleteNote(editNoteId!!)
            }
        }
        navController.navigateUp()
    }
    BackHandler{
        handleBackPress()
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Your Text Note") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            handleBackPress()
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text(text = "Bottom Bar")
            }
        },
        content = {innerPadding->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                OutlinedTextField(
                    value = title, onValueChange ={
                        title =it
                    },
                    label = { Text(text = "Title Here...")},
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = {
                        content = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    label = { Text(text = "Your Content Here...")}
                )
            }

            
        }
    )
}