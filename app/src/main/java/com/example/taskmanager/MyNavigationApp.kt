package com.example.taskmanager

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MyNavigationApp(userViewModel: UserViewModel, taskViewModel: TaskViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(navController, userViewModel) }
        composable("signup") { SignupScreen(navController, userViewModel) }
        composable("dashboard") { DashboardScreen(navController, userViewModel, taskViewModel)}

        composable(
            "profile/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ProfileScreen(navController, userViewModel, taskViewModel)
        }

        composable(
            "taskAdder/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            TaskAdderScreen( userViewModel, taskViewModel, navController)
        }

        composable(
            "editProfile/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            EditProfileScreen(navController, userViewModel)
        }
    }
}