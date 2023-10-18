package cz.mendelu.pef.mapapplication2023.di

import cz.mendelu.pef.mapapplication2023.database.IPlacesLocalRepository
import cz.mendelu.pef.mapapplication2023.database.PlacesDao
import cz.mendelu.pef.mapapplication2023.database.PlacesDatabase
import cz.mendelu.pef.mapapplication2023.database.PlacesLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalPlacesRepository(dao: PlacesDao): IPlacesLocalRepository {
        return PlacesLocalRepositoryImpl(dao)
    }

}