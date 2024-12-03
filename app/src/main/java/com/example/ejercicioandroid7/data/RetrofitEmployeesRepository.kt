package com.example.ejercicioandroid7.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RetrofitEmployeesRepository(
    private val employeeApi: EmployeeApi
) : EmployeesRepository {

    override fun getAllEmployees(): Flow<List<Employee>> = flow {
        try {
            val employees = employeeApi.getAllEmployees()
            emit(employees)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            emit(emptyList())
            // Registrar el error o mostrarlo de alguna forma
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP (errores 4xx, 5xx)
            emit(emptyList())
            // Registrar el error o mostrarlo de alguna forma
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            emit(emptyList())
            // Registrar el error o mostrarlo de alguna forma
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override fun getEmployeeById(id: Int): Flow<Employee?> = flow {
        try {
            val employee = employeeApi.getEmployeeById(id)
            emit(employee)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            emit(null)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            emit(null)
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            emit(null)
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override suspend fun createEmployee(employee: Employee) {
        try {
            employeeApi.createEmployee(employee)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override suspend fun deleteEmployee(employee: Employee) {
        try {
            employeeApi.deleteEmployee(employee.id)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            println("Error desconocido: ${e.localizedMessage}")
        }
    }

    override suspend fun updateEmployee(employee: Employee) {
        try {
            employeeApi.updateEmployee(employee.id, employee)
        } catch (e: IOException) {
            // Manejo de error de red (sin conexión)
            println("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // Manejo de error HTTP
            println("Error HTTP: ${e.localizedMessage}")
        } catch (e: Exception) {
            // Manejo de errores generales
            println("Error desconocido: ${e.localizedMessage}")
        }
    }
}