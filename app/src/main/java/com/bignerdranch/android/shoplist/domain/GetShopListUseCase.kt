package com.bignerdranch.android.shoplist.domain

//добавили в конуструктор класса репозиторий и используем его методы
class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    /**
     * получение списка покупок
     * */
    fun getShopList():List<ShopItem>{
         return shopListRepository.getShopList()
    }
}