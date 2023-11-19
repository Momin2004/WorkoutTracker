package com.example.workouttracker.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class WorkoutViewModel : ViewModel() {
    var currentWorkoutExercises = mutableStateListOf<Exercise>()
        private set

    fun addExerciseToWorkout(exercise: Exercise) {
        currentWorkoutExercises.add(exercise)
    }
}