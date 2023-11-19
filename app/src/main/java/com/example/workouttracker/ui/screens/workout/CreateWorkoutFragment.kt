package com.example.workouttracker.ui.screens.workout

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workouttracker.data.WorkoutViewModel
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import kotlin.math.log

@Composable
fun CreateWorkoutScreen(controller : NavController) {
    val workoutViewModel: WorkoutViewModel = viewModel(LocalContext.current as ComponentActivity)
    WorkoutTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Workout", style = MaterialTheme.typography.h6)
                    },
                    actions = {
                        Text(text = workoutViewModel.timerState.value)
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
    val exercises = workoutViewModel.currentWorkoutExercises
    println("test "+exercises.size)
    LazyColumn {
        items(exercises) { exercise ->
            Text(exercise.name)
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
                    controller.popBackStack()
                    workoutViewModel.stopTimer()
               },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("End Workout")
            }
        }
    }
}


