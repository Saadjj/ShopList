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

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        //проверка
        parseIntent()
       //настройка экрана,  проверяем не было ли его уже создано чтобы не спамить вызовы
        if(savedInstanceState==null){
            launchRightMode()
        }


    }

    private fun launchRightMode() {
      val fragment =   when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
          else->
              throw RuntimeException("Param screen mode is unknown $screenMode")
        }
    //метод для работы с фрагментами
    supportFragmentManager.beginTransaction()
            //используем replace вместо add чтобы oncreate реже вызывался(он замщается )
        .replace(R.id.shop_item_container, fragment)
        .commit()
    }

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