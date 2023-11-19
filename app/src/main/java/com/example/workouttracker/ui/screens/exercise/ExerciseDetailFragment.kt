package com.example.workouttracker.ui.screens.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workouttracker.data.Exercise
import com.example.workouttracker.data.WorkoutDao
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExerciseDetailScreen(exerciseId: Int?, controller: NavController, workoutDao: WorkoutDao) {
    if (exerciseId == null) throw UnsupportedOperationException("Exercise ID is null")

    val exercise by workoutDao.getExerciseById(exerciseId).observeAsState()

    WorkoutTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (exercise != null) {
                            Text(text = exercise!!.name, style = MaterialTheme.typography.h6)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) {
            ExerciseDetailContent(exercise, workoutDao)
        }
    }
}
@Composable
fun ExerciseDetailContent(exercise: Exercise?, workoutDao: WorkoutDao) {
//    val attemptsWithSets = workoutDao.getAttemptsForExercise(exerciseId).observeAsState(listOf()).value
    val mockAttempts = getMockData()
    LazyColumn {
        items(mockAttempts) { attemptWithSets ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Attempt on yyyy-MM-dd",
                        style = MaterialTheme.typography.h6
                    )
                    attemptWithSets.sets.forEach { set ->
                        Text("Set: ${set.setId}, Weight: ${set.weight}, Reps: ${set.reps}")
                    }
                }
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    // Format the timestamp into a readable date string
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(timestamp))
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

data class MockExerciseSet(
    val setId: Int,
    val weight: Double,
    val reps: Int
)

data class MockExerciseAttemptWithSets(
    val attemptId: Int,
    val date: Long, 
    val sets: List<MockExerciseSet>
)

fun getMockData(): List<MockExerciseAttemptWithSets> {
    return listOf(
        MockExerciseAttemptWithSets(
            attemptId = 1,
            date = System.currentTimeMillis(),
            sets = listOf(
                MockExerciseSet(setId = 1, weight = 50.0, reps = 10),
                MockExerciseSet(setId = 2, weight = 55.0, reps = 8)
            )
        ),
        MockExerciseAttemptWithSets(
            attemptId = 2,
            date = System.currentTimeMillis(),
            sets = listOf(
                MockExerciseSet(setId = 1, weight = 55.0, reps = 10),
                MockExerciseSet(setId = 2, weight = 60.0, reps = 8)
            )
        ),

    )
}