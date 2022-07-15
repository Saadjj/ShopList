package com.bignerdranch.android.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.shoplist.R

class MainActivity : AppCompatActivity() {

    //lateinit var чтобы не делать проверку на null
    private lateinit var viewModel: MainViewModel
    private var count =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        //подписываемся на объект shoplist
        viewModel.shopList.observe(this){
            //вывод в лог
            Log.d("MainActivity", it.toString())
            if(count==0){
                count++
                val item=it[0]
                viewModel.deleteShopItem(item)

            }

        }
    }

}