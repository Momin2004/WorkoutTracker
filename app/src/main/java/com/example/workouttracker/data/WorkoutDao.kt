package com.example.workouttracker.data

import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertWorkout(workout: Workout): Long
    @Insert
    suspend fun insertExerciseAttempt(attempt: ExerciseAttempt): Long

    @Insert
    suspend fun insertExerciseSet(set: ExerciseSet)

    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutId = :workoutId")
    fun getWorkoutWithAttempts(workoutId: Int): LiveData<WorkoutWithAttempts>

    @Transaction
    @Query("SELECT * FROM exercise_attempts WHERE attemptId = :attemptId")
    fun getAttemptWithSets(attemptId: Int): LiveData<ExerciseAttemptWithSets>

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Transaction
    @Query("SELECT * FROM exercise_attempts WHERE exerciseId = :exerciseId")
    fun getAttemptsForExercise(exerciseId: Int): LiveData<List<ExerciseAttemptWithSets>>

    @Query("SELECT * FROM exercise WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: Int): LiveData<Exercise>

    @Query("SELECT exerciseId FROM exercise_attempts WHERE attemptId = :attemptId")
    suspend fun getExerciseIdByAttemptId(attemptId: Int): Int?
}

