package com.example.ap2project.di

import android.content.Context
import androidx.room.Room
import com.example.ap2project.Data.dao.database.PrioridadDb
import com.example.ap2project.Data.remote.clienteApi
import com.example.ap2project.Data.remote.prioridadApi
import com.example.ap2project.Data.remote.sistemaApi
import com.example.ap2project.Data.remote.ticketApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    @Provides
    @Singleton
    fun providePrioridadDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

    const val BASE_URL = "https://ap2projectapi.azurewebsites.net/"

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdapter())
            .build()

    @Provides
    @Singleton
    fun providesClienteApi(moshi: Moshi): clienteApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(clienteApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPrioridadApi(moshi: Moshi): prioridadApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(prioridadApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSistemaApi(moshi: Moshi): sistemaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(sistemaApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTicketApi(moshi: Moshi): ticketApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ticketApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPrioridadDao(prioridadDb: PrioridadDb) =
        prioridadDb.prioridadDao()

    @Provides
    @Singleton
    fun providesTicketDao(prioridadDb: PrioridadDb) =
        prioridadDb.ticketDao()
}