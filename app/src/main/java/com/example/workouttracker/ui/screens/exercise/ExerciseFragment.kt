package com.example.workouttracker.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun ExerciseScreen() {
    WorkoutTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Exercise", style = MaterialTheme.typography.h6)
                    }
                )
            }
        ) {
            ExerciseContent()
        }
    }
}

@Composable
fun ExerciseContent() {

}
