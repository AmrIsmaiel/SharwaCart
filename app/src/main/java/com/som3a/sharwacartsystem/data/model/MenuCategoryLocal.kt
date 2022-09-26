package com.som3a.sharwacartsystem.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class MenuCategoryLocal(
    @PrimaryKey
    val id: Int,
    val name: String,
    val icon: String,
    val decscriptionText: String,
    val price: Double,
    val currency: String
    )
