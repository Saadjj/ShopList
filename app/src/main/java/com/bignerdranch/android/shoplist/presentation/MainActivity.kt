package com.bignerdranch.android.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.shoplist.R
import com.bignerdranch.android.shoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    //lateinit var чтобы не делать проверку на nullотложенная инициализация
    private lateinit var viewModel: MainViewModel

    // ссылка на адаптер
    private lateinit var shopListadapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //подписываемся на объект shoplist
        viewModel.shopList.observe(this) {
            //подписались на адаптер?
            shopListadapter.shopList = it
        }
    }

    //настройка работы Recucler VIew
    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        //создание адаптера
        shopListadapter = ShopListAdapter()

        //его установка у recyclerview
        rvShopList.adapter = shopListadapter
        //настройка пула ресайккдл вьюз
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
//место где твориться магия и происходит вызов метода, меняющего аттрибут доступности у шопитема
        shopListadapter.onShopItemLongClickListener ={
            viewModel.changeEnableState(it)
        }
       shopListadapter.onShopItemClickListener={
           Log.d("MainActivity",it.toString())
       }
    }

}