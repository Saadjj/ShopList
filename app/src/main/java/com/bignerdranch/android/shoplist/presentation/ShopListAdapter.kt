package com.bignerdranch.android.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.shoplist.R
import com.bignerdranch.android.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {



    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // как создать вью
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false)
        return ShopItemViewHolder(view)
    }

    // как вставить значения внутри него
    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        val status = if (shopItem.enabled) {
            "Active"
        } else {
            "Not active"
        }

        viewHolder.view.setOnLongClickListener {
            true
        }
        //установка цвета
        //сделать так чтобы использовался нужный макет вьюшки
        if (shopItem.enabled) {
            View enabledView
                )
            )
        } else {

                )
            )
        }
    }



    //собственно получение колличества итемов
    override fun getItemCount(): Int {
        return shopList.size
    }

    //реализация вью холдера
    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }


}