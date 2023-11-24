package com.example.workouttracker.ui.screens.workout

import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workouttracker.Screen
import com.example.workouttracker.ui.screens.workout.WorkoutViewModel


import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WorkoutScreen(controller: NavController) {
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


@Composable
fun WorkoutContent(controller: NavController) {
    val workoutViewModel: WorkoutViewModel = viewModel(LocalContext.current as ComponentActivity)

    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 100.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
// for some reason program just breaks so gl fixing <3
//                CoroutineScope(Dispatchers.IO).launch {
//                    val workoutID = workoutViewModel.insertWorkout()
//                }
                controller.navigate("workout/create")
                workoutViewModel.startWorkout()
            }
        ) {
            Text("Start workout")
        }
    }
}


