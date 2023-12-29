package cz.mendelu.pef.xmichl.bookaroo.di

import android.content.Context
import cz.mendelu.pef.xmichl.bookaroo.communication.book.BookRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.communication.book.BooksApi
import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.GBooksApi
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.GBooksRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.IGBooksRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibrariesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibraryRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.communication.places.IPlacesRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.places.PlacesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.places.PlacesRemoteRepositoryImpl
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
            = LibraryRemoteRepositoryImpl(librariesApi, dataStoreRepository)

    @Provides
    @Singleton
    fun provideBookRepository(
        booksApi: BooksApi,
        dataStoreRepository: DataStoreRepositoryImpl
    ): IBookRemoteRepository
            = BookRemoteRepositoryImpl(booksApi, dataStoreRepository)

    @Provides
    @Singleton
    fun providePlacesRepository(
        placesApi: PlacesApi,
    ): IPlacesRemoteRepository
            = PlacesRemoteRepositoryImpl(placesApi)

    @Provides
    @Singleton
    fun provideGBooksRepository(
        gBooksApi: GBooksApi,
    ): IGBooksRemoteRepository
            = GBooksRemoteRepositoryImpl(gBooksApi)

}
