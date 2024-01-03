package cz.mendelu.pef.examtemplate2023.di


import cz.mendelu.pef.examtemplate2023.communication.ip.API
import cz.mendelu.pef.examtemplate2023.communication.location.LocAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiService(@Named("provideRetrofitIp") retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationApi(@Named("provideRetrofitIpWithLoc") retrofit: Retrofit): LocAPI
            = retrofit.create(LocAPI::class.java)

}