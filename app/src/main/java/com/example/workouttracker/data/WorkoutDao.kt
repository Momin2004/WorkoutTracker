package com.example.workouttracker.data

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
}
