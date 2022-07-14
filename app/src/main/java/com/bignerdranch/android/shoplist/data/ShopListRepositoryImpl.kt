package com.bignerdranch.android.shoplist.data

import com.bignerdranch.android.shoplist.domain.ShopItem
import com.bignerdranch.android.shoplist.domain.ShopListRepository
import java.lang.RuntimeException

/**
 * реализация репозитория из domain-слоя(упрощенная, может позже подрубить БД)
 */
object  ShopListRepositoryImpl:ShopListRepository {
    /**
     * место где храниться список покупок
     */
    private val shopList= mutableListOf<ShopItem>()
    /**
     * хранит айдишник элементов коллекции
     */
    private var autoIncrementID=0

    /**
     * добавление покупки
     */
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id==ShopItem.UNDEFINED_ID){
            shopItem.id= autoIncrementID++
        }//делается проврека , чтобы в editshopitem не возникало ошибки
        shopList.add(shopItem)

    }

    /**
     * удаление покупки
     */
    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    /**
     * получение покупки
     */
    override fun getShopItem(ShopItemId: Int): ShopItem {
        //вся галиматья из-за шопИтема, он нуллабельный, поэтому нужно бросать исключение
         return shopList.find{
             it.id == ShopItemId}
             ?: throw RuntimeException ("Element with shop item $ShopItemId not found")
    }

    /**
     * редактрование покупки
     */
    override fun editShopItemUseCase(shopItem: ShopItem) {
       val oldElement= getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    /**
     * получение списка покупок
     */
    override fun getShopList(): List<ShopItem> {
        //метод toList вызывается для создания копии коллекции, иначе ее можно будет редактировать из любого места программы
        return shopList.toList()
    }
}