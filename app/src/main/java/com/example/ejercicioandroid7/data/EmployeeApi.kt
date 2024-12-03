package com.example.ejercicioandroid7.data

import retrofit2.http.*

interface EmployeeApi {

    @GET("Employees")
    suspend fun getAllEmployees(): List<Employee>

    @GET("Employees/{id}")
    suspend fun getEmployeeById(@Path("id") id: Int): Employee

    @POST("Employees")
    suspend fun createEmployee(@Body newEmployee: Employee): Employee

    @PUT("Employees/{id}")
    suspend fun updateEmployee(
        @Path("id") id: Int,
        @Body updatedEmployee: Employee
    ): Employee

    @DELETE("Employees/{id}")
    suspend fun deleteEmployee(@Path("id") id: Int)
}