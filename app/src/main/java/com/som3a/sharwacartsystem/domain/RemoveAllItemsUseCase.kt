package com.som3a.sharwacartsystem.domain

import io.reactivex.Completable
import javax.inject.Inject

class RemoveAllItemsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Completable =
        repository.clearAllItems()
}