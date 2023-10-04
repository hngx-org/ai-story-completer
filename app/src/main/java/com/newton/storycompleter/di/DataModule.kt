package com.newton.storycompleter.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.newton.storycompleter.app.data.local.StoryCompleterDatabase
import com.newton.storycompleter.repository.StoryRepository
import com.newton.storycompleter.ui.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): StoryCompleterDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = StoryCompleterDatabase::class.java,
            name = StoryCompleterDatabase::class.simpleName
        ).build()
    }

    @Singleton
    @Provides
    fun provideStoryRepository(storyCompleterDatabase: StoryCompleterDatabase): StoryRepository {
        return StoryRepository(
            dao = storyCompleterDatabase.storyDao()
        )
    }
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext


    @Provides
    fun provideAuthService(application: Application):AuthService = AuthService(context = application.applicationContext )
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