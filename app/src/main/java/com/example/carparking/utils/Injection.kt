package com.example.carparking.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.carparking.data.AppRepository
import com.example.carparking.data.local.datastore.DataPreference
import com.example.carparking.data.local.room.ParkingDatabase
import com.example.carparking.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context, dataStore: DataStore<Preferences>): AppRepository {
        val apiService = ApiConfig.provideApiService()
        val preference = DataPreference.getInstance(dataStore)
        val database = ParkingDatabase.getInstance(context)
        val dao = database.placeDao()
        return AppRepository.getInstance(apiService, dao, preference)
    }
}