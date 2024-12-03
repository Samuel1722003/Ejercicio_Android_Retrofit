package com.example.ejercicioandroid7.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http:/direccionbase.app/api/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val employeeApi: EmployeeApi by lazy {
        retrofit.create(EmployeeApi::class.java)
    }
}