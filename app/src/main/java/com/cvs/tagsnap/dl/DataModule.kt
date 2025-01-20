package com.cvs.tagsnap.dl

import com.cvs.tagsnap.data.repository.SnapRepository
import com.cvs.tagsnap.data.repository.SnapRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindFlickrRepository(impl: SnapRepositoryImpl): SnapRepository
}