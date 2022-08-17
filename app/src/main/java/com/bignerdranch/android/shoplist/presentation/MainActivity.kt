package com.bignerdranch.android.shoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    //lateinit var чтобы не делать проверку на nullотложенная инициализация
    private lateinit var viewModel: MainViewModel

    // ссылка на адаптер
    private lateinit var shopListAdapter: ShopListAdapter

    //неинициализированная ссылка на экран в альбомной ориентации
    private var shopItemContainer: FragmentContainerView? = null

    //    добавление отклика на свайп через анонимный объект

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //установка макета
        setContentView(R.layout.activity_main)
        //такой id есть только в макете с альбомной ориентации
        shopItemContainer = findViewById(R.id.shop_item_container)

        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            //подписались на адаптер?
            //при вызове метода сабмит лист вызывается новый поток, в которм происходят все вычисления
            shopListAdapter.submitList(it)
        }
        //добавление кнопки добавления ShopItem
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        //добавление слушателя кликов
        buttonAddItem.setOnClickListener {
            //создание нового экрана
            val intent = ShopItemActivity.newIntentAddItem(this)
            //запускаем
            startActivity(intent)
        }
    }

    private fun isOnPaneMode(): Boolean {
        return shopItemContainer == null
        }


    private fun launchFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container,fragment)
            .commit()
    }

    /**
     * настройка работы Recucler View
     */
    //это   для нормального отображения списка
    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvShopList)

    }

    /**
     * установка обработчика свайпов
     */
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
                    val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                    //удаление элемента
                    viewModel.deleteShopItem(item)
                }
            }

        val itemTouchHelper = ItemTouchHelper(callback)
        //прикрепление
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    /**
     * установка обработчика кликов
     */
    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            Log.d("MainActivity", it.toString())
            //создание нового экрана
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            //запускаем
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


}