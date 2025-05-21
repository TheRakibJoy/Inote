package com.example.inote

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inote.ui.theme.InoteTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import kotlinx.coroutines.DisposableHandle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InoteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InoteNavHost()
                }
            }
        }
    }
}
@SuppressLint("RememberedMutableState")
@Composable
fun InoteNavHost(
    navController: NavHostController = rememberNavController()
){
    val globalViewModel:NoteViewModel = viewModel()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{_,event ->
            if (event==Lifecycle.Event.ON_PAUSE){
                globalViewModel.saveNotesManually()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            HomeScreen(
                onAddNoteClick = {},
                navController = navController,
                viewModel = globalViewModel
            )
        }
        composable("AddText?noteId={noteId}"){
            val noteId = it.arguments?.getString("noteId")
            AddText(navController = navController,
                viewModel = globalViewModel,
                editNoteId = noteId
            )
        }
    }
}


