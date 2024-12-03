package com.example.ejercicioandroid7.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ejercicioandroid7.EmployeesApplication
import com.example.ejercicioandroid7.data.RetrofitInstance
import com.example.ejercicioandroid7.ui.home.HomeViewModel
import com.example.ejercicioandroid7.ui.employee.EmployeeDetailsViewModel
import com.example.ejercicioandroid7.ui.employee.EmployeeEditViewModel
import com.example.ejercicioandroid7.ui.employee.EmployeeEntryViewModel

/**
 * Proporciona Factory para crear instancias de ViewModel para toda la aplicación Empleados,
 * utilizando RetrofitInstance para todas las operaciones de red.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inicializador para EmployeeEditViewModel
        initializer {
            EmployeeEditViewModel(
                savedStateHandle = createSavedStateHandle(),
                employeeApi = RetrofitInstance.employeeApi // Usamos Retrofit para manejar API
            )
        }

        // Inicializador para EmployeeEntryViewModel
        initializer {
            EmployeeEntryViewModel(
                employeeApi = RetrofitInstance.employeeApi // Usamos RetrofitInstance.employeeApi
            )
        }

        // Inicializador para EmployeeDetailsViewModel
        initializer {
            EmployeeDetailsViewModel(
                savedStateHandle = createSavedStateHandle(),
                employeeApi = RetrofitInstance.employeeApi // Usamos RetrofitInstance.employeeApi
            )
        }

        // Inicializador para HomeViewModel
        initializer {
            HomeViewModel(
                employeeApi = RetrofitInstance.employeeApi // Usamos RetrofitInstance.employeeApi
            )
        }
    }
}

/**
 * Función de extensión para consultas del objeto [Application] y devuelve una instancia de
 * [EmployeesApplication].
 */
fun CreationExtras.employeesApplication(): EmployeesApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as EmployeesApplication)