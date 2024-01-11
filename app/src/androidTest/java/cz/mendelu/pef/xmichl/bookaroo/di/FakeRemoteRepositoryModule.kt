package cz.mendelu.pef.xmichl.bookaroo.di

import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.IGBooksRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.places.IPlacesRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.IReaderRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory.BooksFakeRepository
import cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory.GBooksFakeRepository
import cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory.LibraryFakeRepository
import cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory.PlacesFakeRepository
import cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory.ReadersFakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteRepositoryModule::class],
)
abstract class FakeRemoteRepositoryModule {

    // datastore?
    @Binds
    abstract fun provideBookRepository(service: BooksFakeRepository): IBookRemoteRepository

    @Binds
    abstract fun provideRepository(service: ReadersFakeRepository): IReaderRemoteRepository

    @Binds
    abstract fun providePlacesRepository(service: PlacesFakeRepository): IPlacesRemoteRepository

    @Binds
    abstract fun provideGBooksRepository(service: GBooksFakeRepository): IGBooksRemoteRepository

    @Binds
    abstract fun provideLibRepository(service: LibraryFakeRepository): ILibraryRemoteRepository

}
