package com.example.workouttracker.ui.screens.workout

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workouttracker.data.ExerciseSet
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import kotlinx.coroutines.launch

@Composable
fun CreateWorkoutScreen(controller : NavController) {
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
            CreateWorkoutContent(controller)
        }
    }
}
@Composable
fun CreateWorkoutContent(controller: NavController) {
    val workoutViewModel: WorkoutViewModel = viewModel(LocalContext.current as ComponentActivity)
    val currentExercises = workoutViewModel.currentWorkoutExercises
    val exerciseSets = workoutViewModel.exerciseSets

    LazyColumn {
        items(currentExercises) { exercise ->
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(exercise.name, style = MaterialTheme.typography.h6)

                    val sets = exerciseSets[exercise.id] ?: mutableListOf()
                    if (sets.isEmpty()) {
                        workoutViewModel.addSetToAttempt(exercise.id)
                    } else {
                        sets.forEachIndexed { index, set ->
                            SetInputRow(setDetails = set) { weight, reps ->
                                workoutViewModel.updateSet(exercise.id, set.setId, weight, reps)
                                workoutViewModel.onSetDetailsUpdated(exercise.id)
                            }
                        }
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 100.dp),
        ) {
            Button(
                onClick = { controller.navigate("workout/create/add") }
            ) {
                Text("Add Exercise")
            }

            Spacer(modifier = Modifier.width(30.dp))

            Button(
                onClick = {
                    workoutViewModel.saveWorkout {
                        controller.navigate("destination_after_saving")
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("End Workout")
            }
        }
    }
}

//@Composable
//fun ExerciseSetCard(attemptId: Int, sets: MutableList<ExerciseSet>, workoutViewModel: WorkoutViewModel) {
//    Card(modifier = Modifier.padding(8.dp)) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            sets.forEach { set ->
//                SetInputRow(set) { updatedSet ->
//                    workoutViewModel.updateSet(
//                        attemptId,
//                        updatedSet.setId,
//                        updatedSet.weight,
//                        updatedSet.reps
//                    )
//                }
//            }
//            Button(onClick = { workoutViewModel.addSetToAttempt(attemptId) }) {
//                Text("Add Set")
//            }
//        }
//    }
//}

@Composable
fun SetInputRow(setDetails: ExerciseSet, onSetChanged: (Double, Int) -> Unit) {
    println("test")
    var weight by remember { mutableStateOf(setDetails.weight.toString()) }
    var reps by remember { mutableStateOf(setDetails.reps.toString()) }

    Row(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = weight,
            onValueChange = {
                weight = it
                onSetChanged(it.toDoubleOrNull() ?: 0.0, setDetails.reps)
            },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = reps,
            onValueChange = {
                reps = it
                onSetChanged(setDetails.weight, it.toIntOrNull() ?: 0)
            },
            label = { Text("Reps") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}
