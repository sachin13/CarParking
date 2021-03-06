package com.example.carparking.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carparking.data.local.entity.PlacesEntity


@Database(
    entities = [PlacesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ParkingDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var INSTANCE: ParkingDatabase? = null

        fun getInstance(context: Context): ParkingDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingDatabase::class.java,
                    "Location.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
    }
}