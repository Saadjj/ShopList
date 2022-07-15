package com.bignerdranch.android.shoplist.domain

import androidx.lifecycle.LiveData


//добавили в конуструктор класса репозиторий и используем его методы
class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    /**
     * получение списка покупок
     * */
    fun getShopList(): LiveData<List<ShopItem>> {
         return shopListRepository.getShopList()
    }
}