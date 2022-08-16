package com.bignerdranch.android.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.controls.templates.TemperatureControlTemplate.MODE_UNKNOWN
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.shoplist.R
import com.bignerdranch.android.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
//
//    private lateinit var viewModel: ShopItemViewModel
//
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var buttonSave: Button
//
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        //проверка
        parseIntent()
//        //инициализация вьюмодели
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        //инициализация вьюшек
//        initViews()
//        addChangeTextListeners()
//
//        //настройка экрана, если до этого все ок
        launchRightMode()
//        observeViewModel()
    }
//
    private fun launchRightMode() {
      val fragment =   when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
          else->
              throw RuntimeException("Param screen mode is unknown $screenMode")
        }
    //метод для работы с фрагментами
    supportFragmentManager.beginTransaction()
        .add(R.id.shop_item_container, fragment)
        .commit()
    }
//
//
//    private fun addChangeTextListeners(){
//        //при изменении текста убираем исключение от пользователя
//        etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//        })
//
//        etCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//        })
//    }
//    private fun observeViewModel(){
//        //подписываемся на объект ошибки числа
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//            tilCount.error = message
//        }
//        //подписываемся на объект ошибки ввода имени
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//            tilName.error = message
//        }
//        //закрываем экран
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
//    //подписаться на нужын еобъеты лайвдаты
//    private fun launchEditMode() {
//        //получение собственно итема
//        viewModel.getShopItem(shopItemId)
//        //а теперь подписаны на него, и устанавливаем нужные значения
//        viewModel.shopItem.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//        buttonSave.setOnClickListener() {
//            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        //мощь
//        buttonSave.setOnClickListener() {
//            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
//        }
//    }
//
//
//    /**
//     * проверяем входящие параметры интента
//     */
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Param screen mode is unknown $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param EXTRA_SHOP_ITEM_ID is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)

        }
    }
//
//    /**
//     * инициализация вьюшек
//     */
//    private fun initViews() {
//        tilName = findViewById(R.id.til_name)
//        tilCount = findViewById(R.id.til_count)
//        etName = findViewById(R.id.et_name)
//        etCount = findViewById(R.id.et_count)
//        buttonSave = findViewById(R.id.save_button)
//
//    }
//
    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

       /**
        * добавление экрана приложения с добавлением списка покупок
        */
       fun newIntentAddItem(context: Context): Intent {
           val intent = Intent(context, ShopItemActivity::class.java)
           //кладем в интент необходимые параметры
           intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
           return intent

        }
        /**
        * добавление экрана приложения с редактированием списка покупок
       */
       fun newIntentEditItem(context: Context, shopItemId: Int): Intent {

           val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
           return intent
        }
    }

}