package org.k2apps.simplecatsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.k2apps.simplecatsapp.data.repository.remote.CatsApiService
import org.k2apps.simplecatsapp.data.repository.remote.RemoteCatsRepository
import org.k2apps.simplecatsapp.data.repository.remote.RemoteCatsRepository.RemoteCatsRepositoryImpl

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideRemoteCatsRepository(catsApiService: CatsApiService): RemoteCatsRepository =
        RemoteCatsRepositoryImpl(catsApiService)
}