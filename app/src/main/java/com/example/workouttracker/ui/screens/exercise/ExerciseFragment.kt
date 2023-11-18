package com.example.workouttracker.ui.screens.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun ExerciseScreen(controller : NavController) {
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
            ExerciseContent(controller)
        }
    }
}

@Composable
fun ExerciseContent(controller : NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val exercises = listOf("Deadlift", "Squat", "Bench")
    val filteredExercises = exercises.filter { it.contains(searchQuery, ignoreCase = true) }

    Column {
        // Search bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Search Exercises") }
        )

        // Exercise list
        LazyColumn {
            items(filteredExercises) { exercise ->
                ExerciseItem(exercise) {
                    controller.navigate("exercise/$exercise")
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: String, onClick: () -> Unit) {
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

