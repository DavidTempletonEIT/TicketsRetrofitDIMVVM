package com.example.w1d3_rxjavademo.inject

import android.content.Context
import com.example.w1d3_rxjavademo.db.AppDatabase
import com.example.w1d3_rxjavademo.model.network.UrbanRepository
import com.example.w1d3_rxjavademo.network.model.TicketRepositoryImpl
import com.example.w1d3_rxjavademo.model.network.remote.UrbanRestService
import com.example.w1d3_rxjavademo.network.ApiService

class Injection {
    private var userRestService: ApiService? = null
    private var dataBaseInstance: AppDatabase?= null

    fun provideUserRepo(context: Context): UrbanRepository {
        return TicketRepositoryImpl(provideUrbanRestService(), provideAppDatabase(context))
    }

    private fun provideAppDatabase(context: Context): AppDatabase {
        if (dataBaseInstance == null) {
            dataBaseInstance = AppDatabase.getDatabaseInstance(context)
        }
        return dataBaseInstance!!
    }

    private fun provideUrbanRestService(): UrbanRestService {
        if (userRestService == null) {
            userRestService = UrbanRestService.instance
        }
        return userRestService as UrbanRestService
    }
}