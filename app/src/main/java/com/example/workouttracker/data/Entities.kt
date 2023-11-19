package com.example.workouttracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "exercise_sets")
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true) val setId: Int = 0,
    var weight: Double,
    var reps: Int,
    val attemptId: Int // Foreign key to reference ExerciseAttempt
)

@Entity(tableName = "exercise_attempts")
data class ExerciseAttempt(
    @PrimaryKey(autoGenerate = true) val attemptId: Int = 0,
    val workoutId: Int, // Foreign key to reference Workout
    val exerciseId: Int // Foreign key to reference Workout
)

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val workoutId: Int = 0,
    val date: Date
)