package com.example.workouttracker.ui.screens.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@Composable
fun ExerciseDetailItem(exercise: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = exercise, style = MaterialTheme.typography.h6)
        }
    }
}