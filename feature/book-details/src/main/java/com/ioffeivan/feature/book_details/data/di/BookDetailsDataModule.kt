package com.ioffeivan.feature.book_details.data.di

import com.ioffeivan.feature.book_details.data.repository.BookDetailsRepositoryImpl
import com.ioffeivan.feature.book_details.domain.repository.BookDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface BookDetailsDataModule {
    @Binds
    fun bindBookDetailsRepository(impl: BookDetailsRepositoryImpl): BookDetailsRepository
}
