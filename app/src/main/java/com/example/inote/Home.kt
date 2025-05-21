package com.example.inote


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    onAddNoteClick:()-> Unit,
    navController: NavHostController,
    viewModel: NoteViewModel
) {
    Scaffold(
        topBar = {
                 Header(
                     viewModel = viewModel,
                     serchedText = viewModel.searchedText,
                     onQueryChanged = {}
                 )
        },
        floatingActionButton = {

            AddNoteMenu {selectedOption ->
                when(selectedOption){
                    "Text" ->{
                        navController.navigate("AddText")
                    }
                    "Audio"->{

                    }
                    "Image"->{

                    }
                }

            }
        }
    ) {paddingValues ->
        if (viewModel.notes.isEmpty()) {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Surface(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.inverseai_logo),
                            contentDescription = "Inverse AI Logo",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(text = "Welcome to Inote",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(2.dp)
                        )

                        Text(text = "Notes you add will appear here"
                        )
                    }


                }

            }
        }
        else{
            NotesGrid(
                viewModel= viewModel,
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

