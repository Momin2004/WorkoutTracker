package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workouttracker.Screen


import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun WorkoutScreen(controller: NavController) {
    WorkoutTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Workout", style = MaterialTheme.typography.h6)
                    }
                )
            }
        ) {
            WorkoutContent(controller)
        }
    }
}

@Composable
fun WorkoutContent(controller: NavController) {
    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 100.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { controller.navigate("workout/create") }
        ) {
            Text("Start workout")
        }
    }
}



