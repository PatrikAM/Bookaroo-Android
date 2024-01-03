package cz.mendelu.pef.examtemplate2023.di

import cz.mendelu.pef.examtemplate2023.communication.ip.API
import cz.mendelu.pef.examtemplate2023.communication.ip.IRemoteRepository
import cz.mendelu.pef.examtemplate2023.communication.ip.RemoteRepositoryImpl
import cz.mendelu.pef.examtemplate2023.communication.location.LocAPI
import cz.mendelu.pef.examtemplate2023.communication.location.LocIRemoteRepository
import cz.mendelu.pef.examtemplate2023.communication.location.LocRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideIPRemoteRepository(api: API): IRemoteRepository {
        return RemoteRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocRemoteRepository(api: LocAPI): LocIRemoteRepository {
        return LocRemoteRepositoryImpl(api)
    }
}
