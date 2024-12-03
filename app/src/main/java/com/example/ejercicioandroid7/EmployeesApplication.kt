package com.example.ejercicioandroid7

import android.app.Application
import com.example.ejercicioandroid7.data.AppContainer
import com.example.ejercicioandroid7.data.AppDataContainer

class EmployeesApplication : Application() {

    /**
     * Instancia de AppContainer utilizada por el resto de clases para obtener dependencias.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer()
    }
}