package com.som3a.sharwacartsystem.domain

import com.som3a.sharwacartsystem.data.model.Menu
import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {
    fun getItems(): Single<List<MenuCategoryLocal>>

    fun addItem(menuCategoryLocal: MenuCategoryLocal): Single<List<MenuCategoryLocal>>

    fun deleteMenuItem(menuCategoryLocal: MenuCategoryLocal): Single<List<MenuCategoryLocal>>

    fun clearAllItems(): Completable

    fun getRemoteItems(): Single<Menu>
}