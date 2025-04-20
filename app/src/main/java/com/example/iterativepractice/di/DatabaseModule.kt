package com.example.iterativepractice.di

import android.content.Context
import androidx.room.Room
import com.example.iterativepractice.data.local.LocalDatabase
import com.example.iterativepractice.data.local.cartitem.CartItemDao
import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.data.local.product.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProductDao(db: LocalDatabase): ProductDao = db.productDao()

    @Provides
    fun provideCartItemDao(db: LocalDatabase): CartItemDao = db.cartItemDao()

}