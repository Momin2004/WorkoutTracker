package com.example.workouttracker.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class WorkoutViewModel : ViewModel() {
    var currentWorkoutExercises = mutableStateListOf<Exercise>()
        private set

    var timerState = mutableStateOf("00:00")
        private set

    var isWorkoutActive = mutableStateOf(false)
        private set

    private var seconds = 0
    private var timer: Timer? = null
    fun startTimer() {
        isWorkoutActive.value = true
        timer?.cancel()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                seconds++
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                timerState.value = String.format("%02d:%02d:%02d", hours, minutes, secs)
            }
        }, 0, 1000)
    }


    fun stopTimer() {
        isWorkoutActive.value = false
        timer?.cancel()
        timer = null
        seconds = 0
        timerState.value = "00:00"
    }
    fun addExerciseToWorkout(exercise: Exercise) {
        currentWorkoutExercises.add(exercise)
    }
}