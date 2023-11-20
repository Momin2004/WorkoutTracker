package com.example.workouttracker.ui.screens.workout

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workouttracker.data.Exercise
import com.example.workouttracker.data.ExerciseSet
import com.example.workouttracker.ui.screens.workout.WorkoutViewModel
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import androidx.compose.ui.focus.FocusRequester
import java.util.Random
import kotlin.math.log

@Composable
fun CreateWorkoutScreen(controller : NavController) {
    val workoutViewModel: WorkoutViewModel = viewModel(LocalContext.current as ComponentActivity)
    Column {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Workout", style = MaterialTheme.typography.h6)
                    Text(text = workoutViewModel.timerState.value)
                    Button(
                        onClick = {
                            controller.popBackStack()
                            workoutViewModel.stopTimer()
                            // workoutViewModel.saveWorkout {
                            //     controller.navigate("destination_after_saving")
                            // }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("End Workout")
                    }
                }
            }
        )
        CreateWorkoutContent(controller)
    }
}


@Composable
fun CreateWorkoutContent(controller: NavController) {
    val workoutViewModel: WorkoutViewModel = viewModel(LocalContext.current as ComponentActivity)
    val currentExercises = workoutViewModel.currentWorkoutExercises
    val exerciseSets = workoutViewModel.exerciseSets

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { controller.navigate("workout/create/add") },
                modifier = Modifier.padding(bottom = 56.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Exercise")
            }
        }
    ) {
        LazyColumn {
            items(currentExercises) { exercise ->
                Card(modifier = Modifier.padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(exercise.name, style = MaterialTheme.typography.h6)

                        val sets = exerciseSets.filter { it.attemptId == exercise.id }
                        if (sets.isEmpty()) {
                            workoutViewModel.addSetToAttempt(exercise.id)
                        } else {
                            sets.forEachIndexed { index, set ->
                                SetInputRow(setDetails = set, onSetChanged = { weight, reps ->
                                    workoutViewModel.updateSet(exercise.id, set.setId, weight, reps)
                                    workoutViewModel.onSetDetailsUpdated(exercise.id)
                                }, workoutViewModel = workoutViewModel, exercise = exercise)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetInputRow(setDetails: ExerciseSet, onSetChanged: (Double, Int) -> Unit, workoutViewModel: WorkoutViewModel, exercise: Exercise) {
    var weight by remember { mutableStateOf(setDetails.weight.toString()) }
    var reps by remember { mutableStateOf(setDetails.reps) }
    var isNewSetAdded by remember { mutableStateOf(false) }
    var previousReps by remember { mutableStateOf(0) } // Add this line

    Column {
        Row(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight = it
                    onSetChanged(it.toDoubleOrNull() ?: 0.0, reps)
                },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = reps.toString(),
                onValueChange = {
                    val newReps = it.toIntOrNull() ?: 0
                    onSetChanged(weight.toDoubleOrNull() ?: 0.0, newReps)

                    if (previousReps == 0 && newReps in 1..9 && !isNewSetAdded) {
                        workoutViewModel.addSetToAttempt(exercise.id)
                        isNewSetAdded = true
                    }

                    previousReps = newReps // Add this line
                    reps = newReps
                },
                label = { Text("Reps") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
    }
}



