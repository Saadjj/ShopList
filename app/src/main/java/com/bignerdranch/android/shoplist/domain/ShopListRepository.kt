package com.bignerdranch.android.shoplist.domain

import androidx.lifecycle.LiveData


/**
 * умеет (абстрактно) работать с данными для domain слоя
 * по типу черной коробки
 */

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem:ShopItem)

    fun getShopItem(ShopItemId:Int):ShopItem

    fun editShopItemUseCase(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>

}