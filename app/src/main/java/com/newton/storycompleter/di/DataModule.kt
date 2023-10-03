package com.newton.storycompleter.di

import android.content.Context
import androidx.room.Room
import com.newton.storycompleter.data.StoryCompleterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): StoryCompleterDatabase =
        Room.databaseBuilder(context, StoryCompleterDatabase::class.java,"storyCompleter_db")
            .fallbackToDestructiveMigration()
            .build()
    /*@Module
    @InstallIn(SingletonComponent::class)
    interface RepoDataModule{
        @Binds
        @Singleton
        abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl):AuthRemoteDataSource
    }*/


    /*@Provides
    @Singleton
    fun providePreferencesDataStore( @ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(produceFile = {
            context.preferencesDataStoreFile("app_pref")
        })*/

}