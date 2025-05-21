package com.example.inote

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun NotesGrid(
    viewModel: NoteViewModel,
    modifier: Modifier,
    navController: NavHostController
){
    var notes = viewModel.notes
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(notes){note->
            NoteCard(note,
                navController = navController,
                isSelected = viewModel.isNoteSelected(noteId = note.id),
                onClick = {
                    if(viewModel.isAnySelected()){
                        viewModel.toggleNotesSelection(noteId = note.id)
                    }
                    else{
                        navController.navigate("AddText?noteId=${note.id}")
                    }
                },
                onLongClick = {

                    viewModel.toggleNotesSelection(noteId = note.id)
                }
                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    navController: NavHostController,
    isSelected: Boolean,
    onClick:()->Unit,
    onLongClick:() -> Unit


){
    val backgroundColor = if(isSelected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(200.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .background(color = backgroundColor),
    ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
            }

    }
}