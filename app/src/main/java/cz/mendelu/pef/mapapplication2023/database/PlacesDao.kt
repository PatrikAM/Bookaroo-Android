package cz.mendelu.pef.mapapplication2023.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {
    @Query("SELECT * FROM places")
    fun getAll(): Flow<List<Place>>

    @Insert
    suspend fun insert(places: List<Place>)

}
