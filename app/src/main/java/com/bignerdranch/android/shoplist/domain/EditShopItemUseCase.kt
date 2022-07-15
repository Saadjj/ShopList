package com.bignerdranch.android.shoplist.domain

//добавили в конуструктор класса репозиторий и используем его методы
class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    /**
     * редактирование списка покупок
     */
    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItemUseCase(shopItem)
    }
}