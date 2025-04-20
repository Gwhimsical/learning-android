package com.example.iterativepractice.data.local.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao // Data Access Object
interface ProductDao { // CRUD operations interface
    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM Product WHERE id = :id")
    suspend fun getById(id: Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long // Returns ID of inserted row

    @Update
    suspend fun update(product: Product): Int

    @Delete
    suspend fun delete(product: Product): Int
}
