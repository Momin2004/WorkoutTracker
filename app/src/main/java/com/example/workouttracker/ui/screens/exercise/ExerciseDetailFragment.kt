package com.example.workouttracker.ui.screens.exercise

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun ExerciseDetailScreen(exerciseName: String?, controller: NavController) {
    if(exerciseName == null) throw(UnsupportedOperationException());

    WorkoutTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = exerciseName, style = MaterialTheme.typography.h6)
                    },
                    navigationIcon = {
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) {
            ExerciseDetailContent(exerciseName)
        }
    }
}
fun ExerciseDetailContent(exerciseName: String) {

}