package cz.mendelu.pef.xmichl.bookaroo.di

import android.content.Context
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibrariesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibraryRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.IReaderRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.ReaderApi
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.ReaderRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.datastore.IDataStoreRepository
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
    fun provideRepository(readerApi: ReaderApi): IReaderRemoteRepository
            = ReaderRemoteRepositoryImpl(readerApi)

    @Provides
    @Singleton
    fun provideLibRepository(
        librariesApi: LibrariesApi,
        dataStoreRepository: DataStoreRepositoryImpl
    ): ILibraryRemoteRepository
//            = LibraryRemoteRepositoryImpl(librariesApi)
            = LibraryRemoteRepositoryImpl(librariesApi, dataStoreRepository)

}
