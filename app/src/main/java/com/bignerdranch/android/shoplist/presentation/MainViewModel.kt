package com.bignerdranch.android.shoplist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.shoplist.data.ShopListRepositoryImpl
import com.bignerdranch.android.shoplist.domain.DeleteShopItemUseCase
import com.bignerdranch.android.shoplist.domain.EditShopItemUseCase
import com.bignerdranch.android.shoplist.domain.GetShopListUseCase
import com.bignerdranch.android.shoplist.domain.ShopItem

/**
 * главный экран приложения
 */
//вроде как работать нужно через LiveData, чтобы объекты могли спокойно уничтожаться и создаваться,
// поэтому не забыть подписаться на него

//ViewModel хранит текущее состояние данных. Также ViewModel хранит ссылки на один или
// несколько экземпляров Model 'ей, от которых получает данные. ViewModel не располагает информацией,
// откуда данные получены, будь то база данные или внешний сервер.
class MainViewModel : ViewModel() {
    /**
     * временная и неправильная реализация передачи репозитория
     */
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    //это хранилище данных, работающее по паттерну Наблюдатель
    //   в него можно положить какой-либо объект, или подписаться и получить положенный объект
    val shopList = getShopListUseCase.getShopList()


    /**
     * удаление покупки из списка
     */
    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)

    }

    /**
     * измение статуса покупки
     */
    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)

    }
}