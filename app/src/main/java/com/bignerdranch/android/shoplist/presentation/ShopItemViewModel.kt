package com.bignerdranch.android.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.shoplist.data.ShopListRepositoryImpl
import com.bignerdranch.android.shoplist.domain.AddShopItemUseCase
import com.bignerdranch.android.shoplist.domain.EditShopItemUseCase
import com.bignerdranch.android.shoplist.domain.GetShopItemUseCase
import com.bignerdranch.android.shoplist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editeShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopItemUseCase.getShopItem(1)

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }


    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        //проверка на валидность данных
        val fieldValid=validateInput(name,count)
        if(fieldValid){
            val shopItem=ShopItem(name,count,true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editeShopItem(shopItem: ShopItem) {
        editeShopItemUseCase.editShopItem(shopItem)
    }

    /**
     * отдувается за всех, преобразуя поступающие имена в нечто удобоваримое(строка)
     */
    private fun parseName(inputName: String?): String {
        //если инпут не налл то вернем обчну. строку, вырезав пробелы методом trim, иначе пустую строку
        return inputName?.trim() ?: ""
    }

    /**
     * отдувается за всех, преобразуя поступающие чсиловые строки в нечто удобоваримое(число)
     */
    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    /**
     * проверка на адекватность данных
     */
    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        //если строка пуста то
        if (name.isBlank()) {
            //TODO: show error input name
            result = false
        }
        if (count <= 0) {
            //TODO: show error input count
            result = false
        }
        return result


    }
}