package com.bignerdranch.android.shoplist.domain

//добавили в конуструктор класса репозиторий и используем его методы
class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    /**
     * добавление товара в список покупок
     */
    fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}