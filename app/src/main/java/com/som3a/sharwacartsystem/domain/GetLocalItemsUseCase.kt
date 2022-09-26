package com.som3a.sharwacartsystem.domain

import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import io.reactivex.Single
import javax.inject.Inject

class GetLocalItemsUseCase @Inject constructor(
    private val repository: Repository
) {

    fun execute(): Single<List<MenuCategoryLocal>> =
        repository.getItems()
}