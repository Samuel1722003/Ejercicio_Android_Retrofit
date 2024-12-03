package com.example.ejercicioandroid7.ui.employee

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicioandroid7.data.Employee
import com.example.ejercicioandroid7.data.EmployeeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat

class EmployeeEntryViewModel(
    private val employeeApi: EmployeeApi
) : ViewModel() {

    // Cambiar a StateFlow en lugar de mutableStateOf
    private val _employeeUiState = MutableStateFlow(EmployeeUiState())
    val employeeUiState: StateFlow<EmployeeUiState> = _employeeUiState

    // Suponiendo que tienes un método para obtener todos los empleados actuales
    private suspend fun getAllEmployees(): List<Employee> {
        return employeeApi.getAllEmployees() // Asegúrate de tener este método en tu API
    }

    fun updateUiState(employeeDetails: EmployeeDetails) {
        _employeeUiState.value = EmployeeUiState(
            employeeDetails = employeeDetails,
            isEntryValid = validateInput(employeeDetails),
            successMessage = "", // Limpiar mensaje de éxito cuando se actualiza el estado
            error = "" // Limpiar error al actualizar
        )
    }

    fun saveEmployee() {
        if (validateInput()) {
            viewModelScope.launch {
                try {
                    val currentEmployees = getAllEmployees() // Obtener lista de empleados actuales
                    val maxId = currentEmployees.maxOfOrNull { it.id } ?: 0 // Obtener el ID más alto
                    val newEmployeeId = maxId + 1 // Asignar el siguiente ID

                    // Crear el empleado con el nuevo ID
                    val employeeDetails = _employeeUiState.value.employeeDetails.copy(id = newEmployeeId)
                    val employee = employeeDetails.toEmployee()

                    // Guardar el nuevo empleado
                    employeeApi.createEmployee(employee)

                    _employeeUiState.value = _employeeUiState.value.copy(
                        successMessage = "Empleado guardado exitosamente.",
                        error = "" // Limpiar mensaje de error en caso de éxito
                    )
                } catch (e: Exception) {
                    _employeeUiState.value = _employeeUiState.value.copy(
                        successMessage = "", // Limpiar éxito en caso de error
                        error = "Error al guardar empleado: ${e.message}"
                    )
                }
            }
        } else {
            _employeeUiState.value = _employeeUiState.value.copy(error = "Por favor complete todos los campos correctamente.")
        }
    }

    private fun validateInput(employeeDetails: EmployeeDetails = _employeeUiState.value.employeeDetails): Boolean {
        return with(employeeDetails) {
            firstname.isNotBlank() && lastname.isNotBlank() && position.isNotBlank() &&
                    salary.isNotBlank() && yearsofexperience.isNotBlank() &&
                    salary.toIntOrNull() != null && yearsofexperience.toIntOrNull() != null
        }
    }
}

/**
 * Representa el estado de la UI de un empleado.
 */
data class EmployeeUiState(
    val employeeDetails: EmployeeDetails = EmployeeDetails(),
    val isEntryValid: Boolean = false,
    val successMessage: String = "",
    val error: String = ""
)

/**
 * Representa los detalles del empleado en la UI.
 */
data class EmployeeDetails(
    val id: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val position: String = "",
    val salary: String = "",
    val yearsofexperience: String = ""
)

/**
 * Convierte [EmployeeDetails] en [Employee].
 */
fun EmployeeDetails.toEmployee(): Employee = Employee(
    id = id,
    firstName = firstname,
    lastName = lastname,
    position = position,
    salary = salary.toIntOrNull() ?: 0,
    yearsOfExperience = yearsofexperience.toIntOrNull() ?: 0
)

/**
 * Convierte [Employee] en [EmployeeDetails].
 */
fun Employee.toEmployeeDetails(): EmployeeDetails = EmployeeDetails(
    id = id,
    firstname = firstName,
    lastname = lastName,
    position = position,
    salary = salary.toString(),
    yearsofexperience = yearsOfExperience.toString()
)

fun Employee.formatedSalary(): String {
    return NumberFormat.getCurrencyInstance().format(salary)
}