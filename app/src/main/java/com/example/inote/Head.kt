package com.example.inote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    serchedText: MutableState<String>,
    viewModel: NoteViewModel,
    onQueryChanged: (String) -> Unit,
    onMenuClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
){

    Surface (
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp
    ) {
        if(viewModel.isAnySelected()){
            SmallTopAppBar(
                title = {
                Text(text = "${viewModel.selectedNoteIds.size}")
            },
                navigationIcon = {
                                 IconButton(onClick = {
                                     viewModel.clearSelection()
                                 }) {
                                     Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Selection")
                                 }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteSelected()
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Selected")
                    }
                }
            )
        }
        else{
            var searchedText by rememberSaveable {
                serchedText
            }
            CenterAlignedTopAppBar(
                title = {
                    TextField(
                        value = searchedText,
                        onValueChange = {
                            searchedText = it
                        },
                        placeholder = { Text(text = "Search Your Notes...")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        )
                    )
                },
                navigationIcon = {
                                 IconButton(onClick = onMenuClick) {
                                     Icon(
                                         imageVector = Icons.Default.Menu,
                                         contentDescription = "Menu"
                                     )
                                 }
                },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Image(
                            painter = painterResource(id = R.drawable.inverseai_logo), // Replace with your profile image
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    }
}