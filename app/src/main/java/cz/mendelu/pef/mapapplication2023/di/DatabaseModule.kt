package cz.mendelu.pef.mapapplication2023.di

import cz.mendelu.pef.mapapplication2023.MapsApplication
import cz.mendelu.pef.mapapplication2023.database.PlacesDao
import cz.mendelu.pef.mapapplication2023.database.PlacesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(): PlacesDatabase = PlacesDatabase.getDatabase(MapsApplication.appContext)

}