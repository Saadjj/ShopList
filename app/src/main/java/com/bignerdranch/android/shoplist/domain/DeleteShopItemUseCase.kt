package com.bignerdranch.android.shoplist.domain

//добавили в конуструктор класса репозиторий и используем его методы
class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    /**
     * удаление товара из списка покупок
     */
    fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}