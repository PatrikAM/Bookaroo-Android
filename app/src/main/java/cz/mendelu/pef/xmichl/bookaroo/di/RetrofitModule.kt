package cz.mendelu.pef.xmichl.bookaroo.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi
            = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

    @Named("provideRetrofit")
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit
            = Retrofit.Builder()
//        .baseUrl("https://0445-95-82-145-228.ngrok-free.app")
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Named("provideRetrofitPlaces")
    @Provides
    @Singleton
    fun provideRetrofitPlaces(moshi: Moshi): Retrofit
            = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/maps/api/place/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Named("provideRetrofitGBooks")
    @Provides
    @Singleton
    fun provideRetrofitGBooks(moshi: Moshi): Retrofit
            = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
