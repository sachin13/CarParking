package com.example.carparking.data.remote.network

import com.example.carparking.data.remote.response.DetailPlaceResponse
import com.example.carparking.data.remote.response.OccupancyPlaceResponseItem
import com.example.carparking.data.remote.response.PlacesResponseItem
import com.example.carparking.data.remote.response.TokenResponse
import com.example.carparking.model.AndroidModel
import retrofit2.http.*


interface ApiService {
    @POST(" tokenizable")
    suspend fun getToken(
        @Body android: AndroidModel
    ) : TokenResponse

    @GET("read/allPlace")
    suspend fun getAllPlace(
        @Header("Authorization") authorization: String
    ): List<PlacesResponseItem>

    @GET("read/{name}")
    suspend fun getPlace(
        @Header("Authorization") authorization: String,
        @Path("name") name: String
    ): DetailPlaceResponse

    @GET("read/Occupancy/{name}/{floor}")
    suspend fun getOccupancyFloor(
        @Header("Authorization") authorization: String,
        @Path("name") name: String,
        @Path("floor") floor: Int
    ): List<OccupancyPlaceResponseItem>
}