package com.example.workouttracker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workouttracker.ui.screens.exercise.ExerciseScreen
import com.example.workouttracker.ui.screens.statistics.StatisticScreen
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import com.example.workouttracker.R
import com.example.workouttracker.data.WorkoutDao
import com.example.workouttracker.data.WorkoutDatabase
import com.example.workouttracker.ui.screens.exercise.ExerciseDetailScreen
import com.example.workouttracker.ui.screens.workout.WorkoutScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = WorkoutDatabase.getDatabase(this);
            val workoutDao = db.workoutDao();


            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigationBar(navController) }
            ) {
                NavHost(navController = navController, startDestination = "workout") {
                    composable("workout") { WorkoutScreen() }
                    composable("exercises") { ExerciseScreen(navController) }
                    composable("statistics") { StatisticScreen() }
                    composable(
                        "exercise/{exerciseId}",
                        arguments = listOf(navArgument("exerciseId") { type = NavType.IntType })
                        ){ backStackEntry ->
                        val exerciseId = backStackEntry.arguments?.getInt("exerciseId")
                        ExerciseDetailScreen(exerciseId, navController, workoutDao)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Workout,
        Screen.Exercises,
        Screen.Statistics
    )

    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

sealed class Screen(val route: String, val resourceId: Int, val icon: ImageVector) {
    object Workout : Screen("workout", R.string.workout, Icons.Default.Home)
    object Exercises : Screen("exercises", R.string.exercises, Icons.Default.List)
    object Statistics : Screen("statistics", R.string.statistics, Icons.Default.Star)
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkoutTrackerTheme {
        Greeting("Android")
    }
}