package com.bignerdranch.android.shoplist.domain

/**
 * умеет (абстрактно) работать с данными для domain слоя
 * по типу черной коробки
 */

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem:ShopItem)

    fun getShopItem(ShopItemId:Int):ShopItem

    fun editShopItemUseCase(shopItem: ShopItem)

    fun getShopList():List<ShopItem>

}