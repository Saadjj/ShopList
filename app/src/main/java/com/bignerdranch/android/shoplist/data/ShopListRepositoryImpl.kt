package com.bignerdranch.android.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.shoplist.domain.ShopItem
import com.bignerdranch.android.shoplist.domain.ShopListRepository
import java.lang.RuntimeException

/**
 * реализация репозитория из domain-слоя(упрощенная, может позже подрубить БД)
 */
object  ShopListRepositoryImpl:ShopListRepository {

    private val shopListLD=MutableLiveData<List<ShopItem>>()

    /**
     * место где храниться список покупок
     */
    //сортированный список с компаратором
    private val shopList= sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id) })
    /**
     * хранит айдишник элементов коллекции
     */
    private var autoIncrementID=0

    init{
        for(i in 0 until 10){
            val item=ShopItem("Name $i", i, true )
            addShopItem(item)
        }
    }

    /**
     * добавление покупки
     */
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id==ShopItem.UNDEFINED_ID){
            shopItem.id= autoIncrementID++
        }//делается проврека , чтобы в editshopitem не возникало ошибки
        shopList.add(shopItem)
        updateList()

    }

    /**
     * удаление покупки
     */
    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
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
    override fun getShopList(): LiveData<List<ShopItem>> {

        return shopListLD
    }

    /**
     * метод обновления изменяемой лайв даты
     */
    //возвращаем копию для того чтобы с ним нельзя было баловаться извне
    private fun updateList(){
        shopListLD.value= shopList.toList()
    }
}