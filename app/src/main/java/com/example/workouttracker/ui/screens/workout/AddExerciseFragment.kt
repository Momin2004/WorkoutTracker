package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workouttracker.data.Exercise
import com.example.workouttracker.data.WorkoutDao
import com.example.workouttracker.ui.screens.exercise.ExerciseItem
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun AddExerciseScreen(controller : NavController, workoutDao : WorkoutDao) {
    val exercises by workoutDao.getAllExercises().observeAsState()
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
            exercises?.let { AddExerciseContent(controller, it) }
        }
    }
}

@Composable
fun AddExerciseContent(controller: NavController, exercises: List<Exercise>) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredExercises = exercises.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

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
                    controller.navigate("exercise/${exercise.id}")
                }
            }
        }
    }
}
