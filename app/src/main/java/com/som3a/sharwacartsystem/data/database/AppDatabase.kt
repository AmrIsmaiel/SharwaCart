package com.som3a.sharwacartsystem.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal


/**
 * Class to create the local database
 */
@Database(
    entities = [MenuCategoryLocal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemsDAO(): ItemsDAO
}