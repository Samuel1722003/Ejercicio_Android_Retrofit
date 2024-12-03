package com.example.ejercicioandroid7.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicioandroid7.data.Employee
import com.example.ejercicioandroid7.data.EmployeeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para recuperar todos los empleados desde la API usando Retrofit.
 */
class HomeViewModel(private val employeeApi: EmployeeApi) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        fetchEmployees()
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            try {
                // Realizamos la llamada suspendida y obtenemos la lista de empleados directamente
                val employees = employeeApi.getAllEmployees()

                // Actualizamos el estado con los empleados obtenidos
                _homeUiState.value = HomeUiState(employeesList = employees)
            } catch (e: Exception) {
                // Manejamos cualquier error que ocurra durante la llamada
                _homeUiState.value = HomeUiState(error = "Error: ${e.message}")
            }
        }
    }
}

/**
 * Ui State para HomeScreen
 */
data class HomeUiState(
    val employeesList: List<Employee> = listOf(),
    val error: String? = null
)