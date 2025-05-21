package com.example.inote

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.animation.core.tween
import androidx.compose.material.icons.filled.Close

@Composable
fun AddNoteMenu(
    onAddNoteClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Custom dropdown menu above the FAB
        if (expanded) {
            AnimatedVisibility (
                visible = expanded,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 600*2, delayMillis = 200*2)
                ) + expandVertically(
                    animationSpec = tween(durationMillis = 600*2, delayMillis = 200*2)
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 400*2)
                ) + shrinkVertically(
                    animationSpec = tween(durationMillis = 400*2)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(end = 0.dp, bottom = 80.dp, start = 220.dp,top=0.dp) // position above FAB
                        .background(Color.Transparent, shape = RoundedCornerShape(3.dp))
                        .shadow(0.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    MenuItem(icon = Icons.Default.Create, label = "Text") {
                        expanded = false
                        onAddNoteClick("Text")
                    }
                    MenuItem(icon = Icons.Default.Create, label = "Audio") {
                        expanded = false
                        onAddNoteClick("Audio")
                    }
                    MenuItem(icon = Icons.Default.Create, label = "Image") {
                        expanded = false
                        onAddNoteClick("Image")
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(if(!expanded)Icons.Default.Add else Icons.Default.Close, contentDescription = "Add Note")
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            .shadow(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 8.dp).background(color = MaterialTheme.colorScheme.primary,shape = RoundedCornerShape(8.dp))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 20.dp),
                tint = Color.White
            )

            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 20.dp),
                color = Color.White
            )
        }
    }
}