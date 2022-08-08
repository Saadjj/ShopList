package com.bignerdranch.android.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.shoplist.R
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName:TextInputLayout
    private lateinit var tilCount:TextInputLayout
    private lateinit var etName:EditText
    private lateinit var etCount: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        //инициализация вьюмодели
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        //присвоение полученных значений
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        Log.d("ShopItemActivity", mode.toString())
    }

    /**
     * инициализация вьюшек
     */
    private fun initViews(){
        tilName=findViewById(R.id.til_name)
        tilCount=findViewById(R.id.til_count)
        etName=findViewById(R.id.et_name)
        etCount=findViewById(R.id.et_count)
        button==findViewById(R.id.save_button)

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
        fun newIntentEditItem(context: Context, shopItemId:Int): Intent {

            val intent=Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID,shopItemId)
            return intent
        }
    }

}