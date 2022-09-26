package com.som3a.sharwacartsystem.data.model


data class MenuCategory(
    val id: Int,
    val name: String,
    val icon: String,
    val decscriptionText: String,
    val price: Double,
    val currency: String,
    var isAddedToCart: Boolean = false
)

data class Menu(
    val id: Int,
    val name: String,
    val icon: String,
    val menuCategory: List<MenuCategory>
)

object RemoteList {
    val menuList =
        Menu(
            id = 123,
            name = "KFC",
            icon = "kfc",
            menuCategory = listOf(
                MenuCategory(
                    id = 678,
                    name = "dessert 1",
                    icon = "kfc",
                    decscriptionText = "Desert 1 description",
                    price = 12.0,
                    currency = "SR"
                ),
                MenuCategory(
                    id = 1234,
                    name = "dessert 2",
                    icon = "kfc",
                    decscriptionText = "Desert 2 description",
                    price = 14.0,
                    currency = "SR"
                ),
                MenuCategory(
                    id = 9384,
                    name = "Pepsi",
                    icon = "kfc",
                    decscriptionText = "Pepsi description",
                    price = 24.0,
                    currency = "SR"
                ),
                MenuCategory(
                    id = 44433,
                    name = "7 up",
                    icon = "kfc",
                    decscriptionText = "7 up description",
                    price = 14.0,
                    currency = "SR"
                )
            )
        )
}