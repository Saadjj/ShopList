package com.bignerdranch.android.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.shoplist.R
import com.bignerdranch.android.shoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    //lateinit var чтобы не делать проверку на null
    private lateinit var viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llShopList = findViewById(R.id.ll_shop_list)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //подписываемся на объект shoplist
        viewModel.shopList.observe(this) {
            //вывод в лог
            showList(it)

        }
    }

    private fun showList(list: List<ShopItem>) {
        //очистка списка перед обновлением
        llShopList.removeAllViews()

        for (shopItem in list) {
            //получение списка итемов
            val layoutId = if (shopItem.enabled) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }
            //получение списка вьюшек
            val view = LayoutInflater.from(this).inflate(layoutId, llShopList, false)
            // присвоение textView
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvCount = view.findViewById<TextView>(R.id.tv_count)
            tvName.text = shopItem.name
            tvCount.text=shopItem.count.toString()
            //установка активного/неактивного статуса у покупки
            view.setOnLongClickListener{
                viewModel.changeEnableState(shopItem)
                true
            }
            llShopList.addView(view)
        }

    }

}