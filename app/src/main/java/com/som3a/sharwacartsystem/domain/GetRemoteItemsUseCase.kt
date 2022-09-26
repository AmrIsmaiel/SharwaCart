package com.som3a.sharwacartsystem.domain

import com.som3a.sharwacartsystem.data.model.Menu
import io.reactivex.Single
import javax.inject.Inject

class GetRemoteItemsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Single<Menu> = repository.getRemoteItems()
}