package com.example.carparking.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.carparking.data.AppRepository
import com.example.carparking.data.local.entity.PlacesEntity
import kotlinx.coroutines.launch


class FavoriteViewModel(private val repo: AppRepository) : ViewModel() {
    fun loadAllFavoritePlaces() = repo.getFavoritesPlace().asLiveData()

    fun removeFavoritePlaces(place: PlacesEntity, newState: Boolean) {
        viewModelScope.launch {
            place.isFavorite = newState
            repo.updateFavorite(place)
        }
    }
}