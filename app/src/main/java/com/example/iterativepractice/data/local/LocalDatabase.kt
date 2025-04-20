package com.example.iterativepractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
//import androidx.room.Room
//import android.content.Context
import com.example.iterativepractice.data.local.cartitem.CartItem
import com.example.iterativepractice.data.local.cartitem.CartItemDao
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.data.local.product.ProductDao

@Database(entities = [Product::class, CartItem::class], version = 5)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartItemDao(): CartItemDao

    /*companion object { // Singleton pattern
        @Volatile private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "store_database"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }*/
}