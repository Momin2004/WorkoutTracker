package com.example.workouttracker.ui.screens.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workouttracker.data.Exercise
import com.example.workouttracker.data.WorkoutDatabase
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ExerciseScreen(controller : NavController) {
//    val exercises = remember { mutableStateListOf("Deadlift", "Squat", "Bench") }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val database = WorkoutDatabase.getDatabase(context)
    val workoutDao = database.workoutDao()
    val exercises = workoutDao.getAllExercises().observeAsState(listOf()).value
    WorkoutTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Exercise", style = MaterialTheme.typography.h6)
                    },
                )

            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(bottom = 56.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Exercise")
                }
            },

        ) {
            ExerciseContent(controller, exercises)
            if (showDialog) {
                AddExerciseDialog(onAdd = { exerciseName ->
                    CoroutineScope(Dispatchers.IO).launch { workoutDao.insertExercise(Exercise(name = exerciseName)) }
                    showDialog = false
                }, onDismiss = {
                    showDialog = false
                })
            }
        }
    }
}

@Composable
fun ExerciseContent(controller : NavController, exercises: List<Exercise>) {
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

@Composable
fun ExerciseItem(exercise: Exercise, onClick: () -> Unit) {
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
            Text(text = exercise.name, style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
fun AddExerciseDialog(onAdd: (String) -> Unit, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Add Exercise") },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Exercise Name") }
            )
        },
        confirmButton = {
            Button(
                onClick = { if (text.isNotBlank()) onAdd(text) }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

