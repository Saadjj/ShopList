package com.bignerdranch.android.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.shoplist.R

class ShopItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        //присвоение полученных значений
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        Log.d("ShopItemActivity", mode.toString())

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"

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
        fun newIntentEditItem(context: Context): Intent {
        //TODO подумать как передать id в метод
            val intent=Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT,)
            return intent
        }
    }

}