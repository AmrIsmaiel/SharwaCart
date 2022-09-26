package com.som3a.sharwacartsystem.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.som3a.sharwacartsystem.data.model.Menu
import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import com.som3a.sharwacartsystem.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getRemoteItemsUseCase: GetRemoteItemsUseCase,
    private val getLocalItemsUseCase: GetLocalItemsUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val removeAllItemsUseCase: RemoveAllItemsUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val mGetItemsSuccess = MutableLiveData<Menu>()

    fun getItemsUpdated() {
        compositeDisposable.add(
            Single.zip(
                getRemoteItemsUseCase.execute(),
                getLocalItemsUseCase.execute()
            ) { menu, localList ->
                val result = getUIModelsAfterUpdates(localList, menu)
                mGetItemsSuccess.postValue(result)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun getUIModelsAfterUpdates(list: List<MenuCategoryLocal>, menu: Menu): Menu {
        if (list.isNotEmpty()) {
            list.forEach { local ->
                menu.menuCategory.map { remote ->
                    if (local.id == remote.id) {
                        remote.apply { isAddedToCart = true }
                    }
                }
            }
        } else {
            menu.menuCategory.forEach {
                it.apply { isAddedToCart = false }
            }
        }
        return menu
    }

    private fun getUIModelsAfterAddingItems(list: List<MenuCategoryLocal>, menu: Menu): Menu {
        for (local in list.indices) {
            val existed = menu.menuCategory.find { it.id == list[local].id }
            if (existed != null) {
                menu.menuCategory[menu.menuCategory.indexOf(existed)].apply { isAddedToCart = true }
            }
        }
        return menu
    }

    private fun getUIModelsAfterDeletingItems(list: List<MenuCategoryLocal>, menu: Menu): Menu {
        if (list.isNotEmpty()) {
            for (remote in menu.menuCategory.indices) {
                val existed = list.find { it.id == menu.menuCategory[remote].id }
                if (existed == null) {
                    menu.menuCategory[remote].apply { isAddedToCart = false }
                }
            }
        } else {
            menu.menuCategory.forEach {
                it.apply { isAddedToCart = false }
            }
        }
        return menu
    }

    fun clearAllItems() {
        compositeDisposable.add(
            removeAllItemsUseCase.execute()
                .andThen(
                    getRemoteItemsUseCase.execute()
                ).map { menu ->
                    menu.menuCategory.forEach {
                        it.apply { isAddedToCart = false }
                    }
                    return@map menu
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it -> mGetItemsSuccess.postValue(it) }
        )
    }

    fun addItem(menuCategoryLocal: MenuCategoryLocal) {
        compositeDisposable.add(
            Single.zip(
                addItemUseCase.execute(menuCategoryLocal),
                getRemoteItemsUseCase.execute()
            ) { local, remote ->
                val result = getUIModelsAfterAddingItems(local, remote)
                mGetItemsSuccess.postValue(result)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        )
    }

    fun removeItemFromCart(menuCategoryLocal: MenuCategoryLocal) {
        compositeDisposable.add(
            Single.zip(
                removeItemUseCase.execute(menuCategoryLocal),
                getRemoteItemsUseCase.execute()
            ) { local, remote ->
                val result = getUIModelsAfterDeletingItems(local, remote)
                mGetItemsSuccess.postValue(result)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}