package com.example.carparking.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.carparking.data.AppRepository
import com.example.carparking.data.local.entity.PlacesEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: AppRepository) : ViewModel() {
    fun loadDetailParkingPlace(token: String, name: String) =
        repo.getPlaceDetail(token, name).asLiveData()

    fun loadTokenFromDataStore() = repo.getTokenFromDataStore().asLiveData()

    fun requestToken() {
        viewModelScope.launch {
            repo.tokenKey()
        }
    }

    fun addPlace(place: PlacesEntity) {
        viewModelScope.launch {
            repo.insertPlaces(place)
        }
    }

    fun updateFavoritePlace(name: String, newState: Boolean) {
        viewModelScope.launch {
            repo.updateFavoriteDetail(name, newState)
        }
    }
}