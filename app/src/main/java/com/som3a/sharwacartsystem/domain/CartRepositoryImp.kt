package com.som3a.sharwacartsystem.domain

import com.som3a.sharwacartsystem.data.model.Menu
import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import com.som3a.sharwacartsystem.data.source.CartLocalDataSource
import com.som3a.sharwacartsystem.data.source.CartRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CartRepositoryImp @Inject constructor(
    private val cartLocalDataSource: CartLocalDataSource,
    private val cartRemoteDataSource: CartRemoteDataSource
) : Repository {

    override fun getItems(): Single<List<MenuCategoryLocal>> =
        cartLocalDataSource.getItems()

    override fun addItem(menuCategoryLocal: MenuCategoryLocal): Single<List<MenuCategoryLocal>> {
        return cartLocalDataSource.addItem(menuCategoryLocal).andThen(
            cartLocalDataSource.getItems()
        )
    }

    override fun deleteMenuItem(menuCategoryLocal: MenuCategoryLocal): Single<List<MenuCategoryLocal>> {
        return cartLocalDataSource.getItems().flatMap {
            return@flatMap if (it.isNotEmpty() || it.size != 1) {
                cartLocalDataSource.deleteMenuItem(menuCategoryLocal.id).andThen(
                    cartLocalDataSource.getItems()
                )
            } else {
                cartLocalDataSource.clearAllItems().andThen(
                    Single.just(emptyList())
                )
            }
        }
    }

    override fun clearAllItems(): Completable {
        return cartLocalDataSource.clearAllItems()
    }

    override fun getRemoteItems(): Single<Menu> = cartRemoteDataSource.getMenuList()
}