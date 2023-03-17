package com.example.nycschools.di

import com.example.nycschools.rest.NetworkRepository
import com.example.nycschools.rest.NetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Abstract class RepositoryModule
 * @author Sara Iza
 * Provides dependencies for Repository Components
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesNetworkRepository(
        networkRepositoryImpl: NetworkRepositoryImpl
    ): NetworkRepository

}