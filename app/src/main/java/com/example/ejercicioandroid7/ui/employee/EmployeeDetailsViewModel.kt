package com.example.ejercicioandroid7.ui.employee

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicioandroid7.data.EmployeeApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para recuperar, actualizar y eliminar un elemento a través de la API de Retrofit.
 */
class EmployeeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val employeeApi: EmployeeApi
) : ViewModel() {

    private val employeeId: Int = checkNotNull(savedStateHandle[EmployeeDetailsDestination.employeeIdArg])

    private val _uiState = MutableStateFlow(EmployeeDetailsUiState())
    val uiState: StateFlow<EmployeeDetailsUiState> = _uiState.asStateFlow()

    init {
        // Cargar los detalles del empleado al inicializar el ViewModel
        viewModelScope.launch {
            try {
                val employee = employeeApi.getEmployeeById(employeeId)
                _uiState.value = EmployeeDetailsUiState(
                    outOfStock = employee.salary <= 0,
                    employeeDetails = employee.toEmployeeDetails()
                )
            } catch (e: Exception) {
                _uiState.value = EmployeeDetailsUiState(error = "Error al cargar empleado: ${e.message}")
            }
        }
    }

    suspend fun deleteEmployee() {
        viewModelScope.launch {
            try {
                employeeApi.deleteEmployee(employeeId)
                _uiState.value = EmployeeDetailsUiState(successMessage = "Empleado eliminado con éxito")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Error al eliminar empleado: ${e.message}")
            }
        }
    }
}

/**
 * Estado de la interfaz de usuario para EmployeeDetailsScreen
 */
data class EmployeeDetailsUiState(
    val outOfStock: Boolean = true,
    val employeeDetails: EmployeeDetails = EmployeeDetails(),
    val error: String? = null,
    val successMessage: String? = null
)