package com.example.ejercicioandroid7.data

/**
 * Contenedor de aplicaciones para inyección de dependencias.
 */
interface AppContainer {
    val employeesRepository: EmployeesRepository
}

/**
 * [AppContainer] implementación que proporciona instancia de [EmployeeApi] usando Retrofit.
 */
class AppDataContainer : AppContainer {
    /**
     * Implementación para [EmployeesRepository] usando Retrofit.
     */
    override val employeesRepository: EmployeesRepository by lazy {
        RetrofitEmployeesRepository(RetrofitInstance.employeeApi)
    }
}