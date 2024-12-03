package com.example.ejercicioandroid7.ui.employee

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicioandroid7.data.EmployeeApi
import kotlinx.coroutines.launch

/**
 * ViewModel para recuperar y actualizar un empleado mediante Retrofit.
 */
class EmployeeEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val employeeApi: EmployeeApi
) : ViewModel() {

    var employeeUiState by mutableStateOf(EmployeeUiState())
        private set

    private val employeeId: Int = checkNotNull(savedStateHandle[EmployeeEditDestination.employeeIdArg])

    init {
        viewModelScope.launch {
            try {
                val employee = employeeApi.getEmployeeById(employeeId)
                employeeUiState = employee?.toEmployeeDetails()?.let {
                    EmployeeUiState(employeeDetails = it, isEntryValid = validateInput(it))
                } ?: employeeUiState.copy(error = "Empleado no encontrado.")
            } catch (e: Exception) {
                employeeUiState = employeeUiState.copy(error = "Error al recuperar empleado: ${e.message}")
            }
        }
    }

    fun updateEmployee() {
        if (validateInput(employeeUiState.employeeDetails)) {
            viewModelScope.launch {
                try {
                    val employee = employeeUiState.employeeDetails.toEmployee()
                    employeeApi.updateEmployee(employee.id, employee)
                    employeeUiState = employeeUiState.copy(successMessage = "Empleado actualizado exitosamente.")
                } catch (e: Exception) {
                    employeeUiState = employeeUiState.copy(error = "Error al actualizar empleado: ${e.message}")
                }
            }
        }
    }

    fun updateUiState(employeeDetails: EmployeeDetails) {
        employeeUiState = EmployeeUiState(
            employeeDetails = employeeDetails,
            isEntryValid = validateInput(employeeDetails)
        )
    }

    private fun validateInput(uiState: EmployeeDetails): Boolean {
        return uiState.firstname.isNotBlank() && uiState.lastname.isNotBlank() &&
                uiState.position.isNotBlank() && uiState.salary.isNotBlank() &&
                uiState.yearsofexperience.isNotBlank()
    }
}