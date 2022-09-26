package com.som3a.sharwacartsystem.injection

import android.content.Context
import androidx.room.Room
import com.som3a.sharwacartsystem.data.database.AppDatabase
import com.som3a.sharwacartsystem.data.database.ItemsDAO
import com.som3a.sharwacartsystem.data.source.CartLocalDataSource
import com.som3a.sharwacartsystem.data.source.CartRemoteDataSource
import com.som3a.sharwacartsystem.domain.*
import com.som3a.sharwacartsystem.presentation.CartViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CartModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "database")
            .build()
    }

    @Provides
    @Singleton
    fun provideItemsDAO(appDatabase: AppDatabase) = appDatabase.itemsDAO()

    @Provides
    fun providesLocalDataSource(itemsDAO: ItemsDAO) = CartLocalDataSource(itemsDAO)

    @Provides
    fun providesRemoteDataSource() = CartRemoteDataSource()

    @Provides
    fun providesCartRepositoryImp(
        cartLocalDataSource: CartLocalDataSource,
        cartRemoteDataSource: CartRemoteDataSource
    ): Repository = CartRepositoryImp(cartLocalDataSource, cartRemoteDataSource)

    @Provides
    fun providesViewModel(
        getRemoteItemsUseCase: GetRemoteItemsUseCase,
        getLocalItemsUseCase: GetLocalItemsUseCase,
        addItemUseCase: AddItemUseCase,
        removeItemUseCase: RemoveItemUseCase,
        removeAllItemsUseCase: RemoveAllItemsUseCase
    ) = CartViewModel(
        getRemoteItemsUseCase,
        getLocalItemsUseCase,
        addItemUseCase,
        removeItemUseCase,
        removeAllItemsUseCase
    )
}