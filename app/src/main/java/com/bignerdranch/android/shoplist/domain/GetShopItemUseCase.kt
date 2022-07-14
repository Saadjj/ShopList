package com.bignerdranch.android.shoplist.domain

//добавили в конуструктор класса репозиторий и используем его методы
class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    /**
     * получение товара из  списка покупок
     */
    fun getShopItem(ShopItemId:Int):ShopItem{
       return shopListRepository.getShopItem(ShopItemId)
    }
}