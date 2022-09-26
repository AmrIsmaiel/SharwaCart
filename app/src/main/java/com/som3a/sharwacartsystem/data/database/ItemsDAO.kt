package com.som3a.sharwacartsystem.data.database

import androidx.room.*
import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * class represent the queries we can make
 * on the database
 */
@Dao
interface ItemsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMenuItem(item: MenuCategoryLocal): Completable

    @Query("SELECT * FROM items")
    fun getMenuItems(): Single<List<MenuCategoryLocal>>

    @Transaction
    @Query("SELECT * FROM items WHERE id = :itemId")
    fun getMenuItem(itemId: Int): Single<MenuCategoryLocal>

    @Query("DELETE FROM items WHERE id = :id")
    fun deleteMenuItem(id: Int): Completable

    @Query("DELETE FROM items")
    fun clearCachedMenuItems(): Completable
}