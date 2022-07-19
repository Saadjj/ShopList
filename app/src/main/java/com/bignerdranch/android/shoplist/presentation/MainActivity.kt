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
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //подписываемся на объект shoplist
        viewModel.shopList.observe(this) {
            //подписались на адаптер?
            adapter.shopList = it
        }
    }

    //настройка работы Recucler VIew
    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        //создание адаптера
        adapter = ShopListAdapter()
        //его установка у recyclerview
        rvShopList.adapter = adapter


    }
}