package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

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
                onClick = { controller.popBackStack() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("End Workout")
            }
        }
    }
}


