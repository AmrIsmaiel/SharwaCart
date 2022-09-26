package com.som3a.sharwacartsystem.domain

import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AddItemUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(menuCategoryLocal: MenuCategoryLocal): Single<List<MenuCategoryLocal>> =
        repository.addItem(menuCategoryLocal)
}