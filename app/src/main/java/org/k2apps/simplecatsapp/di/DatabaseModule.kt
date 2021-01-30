package org.k2apps.simplecatsapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.k2apps.simplecatsapp.data.repository.db.*

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideCatsDao(@ApplicationContext appContext: Context): CatsDao =
        AppDatabase.getDatabase(appContext).catsDao()

    @Provides
    fun provideCatsRepository(catsDao: CatsDao) = CatsRepository(catsDao)

    @Provides
    fun provideBreedDao(@ApplicationContext appContext: Context): BreedsDao =
        AppDatabase.getDatabase(appContext).breedsDao()

    @Provides
    fun provideBreedRepository(breedsDao: BreedsDao) = BreedsRepository(breedsDao)
}