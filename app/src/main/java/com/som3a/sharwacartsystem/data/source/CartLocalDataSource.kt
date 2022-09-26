package com.som3a.sharwacartsystem.data.source

import com.som3a.sharwacartsystem.data.database.ItemsDAO
import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CartLocalDataSource @Inject constructor(private val itemsDAO: ItemsDAO) {

    fun getItems(): Single<List<MenuCategoryLocal>> =
        itemsDAO.getMenuItems()

    fun addItem(menuCategoryLocal: MenuCategoryLocal): Completable =
        itemsDAO.addMenuItem(menuCategoryLocal)

    fun deleteMenuItem(id: Int): Completable = itemsDAO.deleteMenuItem(id)

    fun clearAllItems(): Completable = itemsDAO.clearCachedMenuItems()
}