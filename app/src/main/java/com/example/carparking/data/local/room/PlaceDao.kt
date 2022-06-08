package com.example.carparking.data.local.room

import androidx.room.*
import com.example.carparking.data.local.entity.PlacesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaceDao {

    // Place Operations
    @Query("SELECT * FROM places WHERE isFavorite = 1 ORDER BY name")
    fun getFavorites(): Flow<List<PlacesEntity>>

    @Query("SELECT * FROM places WHERE isAlreadySee = 1 ORDER BY insertAt DESC")
    fun getHistories(): Flow<List<PlacesEntity>>

    @Query("SELECT * FROM places WHERE isAlreadySee = 1 ORDER BY insertAt DESC LIMIT 5")
    fun getRecentPlaces(): Flow<List<PlacesEntity>>

    @Query("UPDATE places SET isAlreadySee = 0 WHERE isAlreadySee = 1")
    suspend fun removeHistories()

    @Query("SELECT * FROM places WHERE name = :name LIMIT 1")
    fun getPlace(name: String): Flow<PlacesEntity?>

    @Query("UPDATE places SET isFavorite = :newState WHERE name = :name")
    suspend fun updateFavoritePlace(name: String, newState: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlacesEntity)

    @Update
    suspend fun updatePlaces(place: PlacesEntity)
}