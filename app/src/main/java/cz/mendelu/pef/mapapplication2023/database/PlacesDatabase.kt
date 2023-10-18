package cz.mendelu.pef.mapapplication2023.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Place::class], version = 2, exportSchema = false)
abstract class PlacesDatabase : RoomDatabase() {

    abstract fun placesDao(): PlacesDao

    companion object {

        private var INSTANCE: PlacesDatabase? = null

        fun getDatabase(context: Context): PlacesDatabase {
            if (INSTANCE == null) {
                synchronized(PlacesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PlacesDatabase::class.java, "places_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
