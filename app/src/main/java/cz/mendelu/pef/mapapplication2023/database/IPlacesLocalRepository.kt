package cz.mendelu.pef.mapapplication2023.database

import kotlinx.coroutines.flow.Flow

interface IPlacesLocalRepository {
    fun getAll(): Flow<List<Place>>
    suspend fun insert(places: List<Place>)
}

