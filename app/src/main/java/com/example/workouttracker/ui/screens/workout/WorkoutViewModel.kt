package com.example.workouttracker.ui.screens.workout

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.Timer
import java.util.TimerTask

class WorkoutViewModel : ViewModel() {
    private var newWorkout = Workout(workoutId = 0, date = Date(0))
    private var newExerciseAttempts = mutableStateListOf<ExerciseAttempt>()
    var exerciseSets = mutableStateListOf<ExerciseSet>()
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
        exerciseSets.add(ExerciseSet(setId = exerciseSets.size, weight = 0.0, reps = 0, attemptId = attemptId))
    }

    fun updateSet(attemptId: Int, setId: Int, weight: Double, reps: Int) {
        exerciseSets.find { it.setId == setId && it.attemptId == attemptId }?.apply {
            this.weight = weight
            this.reps = reps
        }
    }
    fun onSetDetailsUpdated(attemptId: Int) {
        val sets = exerciseSets.filter { it.attemptId == attemptId }
        if (sets.isNotEmpty() && sets.last().isFilled()) {
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

    var seconds = 0
    var timerState = mutableStateOf("")
    var isWorkoutActive = mutableStateOf(false)

    private var timerJob: Job? = null
    private var startTime = 0L

    fun startTimer() {
        isWorkoutActive.value = true

        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Main) {
            while (isActive) {
                val elapsedTime = System.currentTimeMillis() - startTime
                val hours = elapsedTime / 3600000
                val minutes = (elapsedTime % 3600000) / 60000
                val secs = (elapsedTime % 60000) / 1000
                timerState.value = String.format("%02d:%02d:%02d", hours, minutes, secs)
                delay(1000)
            }
        }
    }
    fun iniTimer() {
        startTime = System.currentTimeMillis()
        startTimer()
    }

    fun stopTimer() {
        isWorkoutActive.value = false
        timerJob?.cancel()
        timerJob = null
        seconds = 0
        timerState.value = "00:00"
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
    }
    fun startWorkout() {
        currentWorkoutExercises.clear()
        exerciseSets.clear()
        iniTimer()
    }
}

