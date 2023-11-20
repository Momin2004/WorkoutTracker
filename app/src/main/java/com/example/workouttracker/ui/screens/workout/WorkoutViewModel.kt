package com.example.workouttracker.ui.screens.workout

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.*
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.Timer
import java.util.TimerTask

class WorkoutViewModel : ViewModel() {
    private var newWorkout = Workout(workoutId = 0, date = Date(0))
    private var newExerciseAttempts = mutableStateListOf<ExerciseAttempt>()
    var exerciseSets = mutableStateMapOf<Int, MutableList<ExerciseSet>>()
    var currentWorkoutExercises = mutableStateListOf<Exercise>()

    fun addExerciseToWorkout(exercise: Exercise) {
        currentWorkoutExercises.add(exercise)

        val attemptId = newExerciseAttempts.size + 1
        val newAttempt = ExerciseAttempt(
            attemptId = attemptId,
            workoutId = newWorkout.workoutId,
            exerciseId = exercise.id
        )
        newExerciseAttempts.add(newAttempt)
        // exerciseSets[attemptId] = mutableListOf(ExerciseSet(setId = 0, weight = 0.0, reps = 0, attemptId = attemptId))
    }

    fun addSetToAttempt(attemptId: Int) {
        val sets = exerciseSets.getOrPut(attemptId) { mutableListOf() }
        sets.add(ExerciseSet(setId = sets.size, weight = 0.0, reps = 0, attemptId = attemptId))
    }

    fun updateSet(attemptId: Int, setId: Int, weight: Double, reps: Int) {
        exerciseSets[attemptId]?.find { it.setId == setId }?.apply {
            this.weight = weight
            this.reps = reps
        }
    }

    fun onSetDetailsUpdated(attemptId: Int) {
        val sets = exerciseSets[attemptId]
        if (sets != null && sets.last().isFilled()) {
            addSetToAttempt(attemptId)
        }
    }

    fun addSetIfLastSetIsFilled(attemptId: Int) {
        val sets = exerciseSets[attemptId]
        if (sets != null && sets.last().isFilled()) {
            addSetToAttempt(attemptId)
        }
    }

    private fun ExerciseSet.isFilled() = weight > 0.0 && reps > 0

    fun saveWorkout(onSaved: () -> Unit) {
        viewModelScope.launch {
            //todo
            onSaved()
        }
    }

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
}

