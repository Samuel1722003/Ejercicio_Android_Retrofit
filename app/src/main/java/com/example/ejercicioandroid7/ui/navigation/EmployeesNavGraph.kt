package com.example.ejercicioandroid7.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ejercicioandroid7.ui.home.HomeDestination
import com.example.ejercicioandroid7.ui.home.HomeScreen
import com.example.ejercicioandroid7.ui.employee.EmployeeDetailsDestination
import com.example.ejercicioandroid7.ui.employee.EmployeeDetailsScreen
import com.example.ejercicioandroid7.ui.employee.EmployeeEditDestination
import com.example.ejercicioandroid7.ui.employee.EmployeeEditScreen
import com.example.ejercicioandroid7.ui.employee.EmployeeEntryDestination
import com.example.ejercicioandroid7.ui.employee.EmployeeEntryScreen

/**
 * Proporciona un gráfico de navegación para la aplicación.
 */
@Composable
fun EmployeesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToEmployeeEntry = { navController.navigate(EmployeeEntryDestination.route) },
                navigateToEmployeeUpdate = {
                    navController.navigate("${EmployeeDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = EmployeeEntryDestination.route) {
            EmployeeEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = EmployeeDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(EmployeeDetailsDestination.employeeIdArg) {
                type = NavType.IntType
            })
        ) {
            EmployeeDetailsScreen(
                navigateToEditItem = { navController.navigate("${EmployeeEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = EmployeeEditDestination.routeWithArgs,
            arguments = listOf(navArgument(EmployeeEditDestination.employeeIdArg) {
                type = NavType.IntType
            })
        ) {
            EmployeeEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}