package com.example.workouttracker.data

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseAttemptWithSets(
    @Embedded val attempt: ExerciseAttempt,
    @Relation(
        parentColumn = "attemptId",
        entityColumn = "attemptId"
    )
    val sets: List<ExerciseSet>
)

data class WorkoutWithAttempts(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "workoutId",
        entity = ExerciseAttempt::class
    )
    val attempts: List<ExerciseAttemptWithSets>
)

data class ExerciseWithAttempts(
    @Embedded val attempt: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseId",
        entity = ExerciseAttempt::class
    )
    val exercise: Exercise
)