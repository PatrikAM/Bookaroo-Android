package cz.mendelu.pef.xmichl.bookaroo.di

import cz.mendelu.pef.xmichl.bookaroo.communication.book.BooksApi
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.GBooksApi
import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibrariesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.places.PlacesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.ReaderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideReaderApi(@Named("provideRetrofit") retrofit: Retrofit): ReaderApi
            = retrofit.create(ReaderApi::class.java)

    @Provides
    @Singleton
    fun provideLibrariesApi(@Named("provideRetrofit") retrofit: Retrofit): LibrariesApi
            = retrofit.create(LibrariesApi::class.java)

    @Provides
    @Singleton
    fun provideBooksApi(@Named("provideRetrofit") retrofit: Retrofit): BooksApi
            = retrofit.create(BooksApi::class.java)

    @Provides
    @Singleton
    fun providePlacesApi(@Named("provideRetrofitPlaces") retrofitPlaces: Retrofit): PlacesApi
            = retrofitPlaces.create(PlacesApi::class.java)

    @Provides
    @Singleton
    fun provideGBooksApi(@Named("provideRetrofitGBooks") retrofitGBooks: Retrofit): GBooksApi
            = retrofitGBooks.create(GBooksApi::class.java)

}
