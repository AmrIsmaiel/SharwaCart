package com.som3a.sharwacartsystem.data.source

import com.som3a.sharwacartsystem.data.model.Menu
import com.som3a.sharwacartsystem.data.model.RemoteList
import io.reactivex.Single
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor() {

    fun getMenuList(): Single<Menu> = Single.just(RemoteList.menuList)
}