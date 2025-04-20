package com.example.iterativepractice.data.local.product

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double
)