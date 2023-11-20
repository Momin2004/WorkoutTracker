package com.example.workouttracker.ui.screens.statistics

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun StatisticScreen() {
    WorkoutTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Statistics", style = MaterialTheme.typography.h6)
                    }
                )
            }
        ) {
            StatisticContent()
        }
    }
}

@Composable
fun StatisticContent() {

}
