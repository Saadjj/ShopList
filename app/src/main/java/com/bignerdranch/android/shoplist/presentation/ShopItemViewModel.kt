package com.bignerdranch.android.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    /**
     * объект LiveDate, используемый для отображения ошибок имени
     */
    //делаем ее приватной и вообще заморачиваемся с геттером ради того чтобы
    // ее невозможно было изменить извне класса
    private val _errorInputName = MutableLiveData<Boolean>()

    //часть заморочки-извне только получаем но не меняем
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    /**
     * объект LiveDate, используемый для отображения ошибок счета
     */
    // то же что и другая переменная
    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    //создание муты для ShopItema
    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    /**
     * переменная закрытия экрана
     *///unit - эт что-то на продвитом
    private val _flagScrenClosing=MutableLiveData<Unit>()
    val flagScreenClosing: LiveData<Unit>
    get()=_flagScrenClosing

    /**
     * получение объекта покупок
     */
    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    /**
     * добавление списка покупок
     */
    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        //проверка на валидность данных
        val fieldValid = validateInput(name, count)
        if (fieldValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            screnClosing()
        }


    }

    /**
     * редкатирование списка покупок
     */
    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        //проверка на валидность данных
        val fieldValid = validateInput(name, count)
        if (fieldValid) {
          _shopItem.value?.let {
              val item=it.copy(name=name,count=count)
              editShopItemUseCase.editShopItem(item)
              screnClosing()
          }

        }
    }

    /**
     * передаем unit якобы из-за того что что этот объект больше не будем использовать в activity
     */
    private fun screnClosing() {
        _flagScrenClosing.value =Unit
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
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    /**
     * сброс ошибки
     */
    private fun resetErrorInputName() {
        _errorInputName.value = true
    }

    /**
     * сброс имени
     */
    private fun resetErrorInputCount() {
        _errorInputName.value = true
    }


}