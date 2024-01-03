package cz.mendelu.pef.examtemplate2023.di

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
    fun provideMoshiConverter(): Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    @Named("provideRetrofitIp")
    @Provides
    @Singleton
    fun provideRetrofitIp(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.ipify.org/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Named("provideRetrofitIpWithLoc")
    @Provides
    @Singleton
    fun provideRetrofitIpWithLoc(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("https://akela.mendelu.cz/~xlanda/78.128.147.194/geo/")
            .baseUrl("https://akela.mendelu.cz/~xlanda/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}