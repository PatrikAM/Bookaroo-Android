package cz.mendelu.pef.xmichl.bookaroo.di

import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibrariesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.ReaderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideReaderApi(retrofit: Retrofit): ReaderApi
            = retrofit.create(ReaderApi::class.java)

    @Provides
    @Singleton
    fun provideLibrariesApi(retrofit: Retrofit): LibrariesApi
            = retrofit.create(LibrariesApi::class.java)

}
