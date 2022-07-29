package com.bignerdranch.android.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.shoplist.R
import com.bignerdranch.android.shoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    //lateinit var чтобы не делать проверку на nullотложенная инициализация
    private lateinit var viewModel: MainViewModel

    // ссылка на адаптер
    private lateinit var shopListadapter: ShopListAdapter

    //    добавление отклика на свайп через анонимный объект

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //подписываемся на объект shoplist
        viewModel.shopList.observe(this) {
            //подписались на адаптер?
            //при вызове метода сабмит лист вызывается новый поток, в которм происходят все вычисления
            shopListadapter.submitList(it)
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
        setUpLongClickListener()
        setUpClickListener()
        setupSwipeListener(rvShopList)

    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback =
            //абстрактный класс
            object : ItemTouchHelper.SimpleCallback(
                //поскольку с перемещением не работаем то пердаем 0
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                //полськоку он не нужен возрвщаем фолз
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                //получение тиема по которому произошел свайп и удаление его из коллекции
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //получение элемента
                    val item = shopListadapter.currentList[viewHolder.adapterPosition]
                    //удаление элемента
                    viewModel.deleteShopItem(item)
                }
            }

        val itemTouchHelper = ItemTouchHelper(callback)
        //прикрепление
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setUpClickListener() {
        shopListadapter.onShopItemClickListener = {
            Log.d("MainActivity", it.toString())
        }
    }

    private fun setUpLongClickListener() {
        shopListadapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }




}