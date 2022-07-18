package com.bignerdranch.android.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.shoplist.R
import com.bignerdranch.android.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    val list = listOf<ShopItem>()

    // как создать вью
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false)
        return ShopItemViewHolder(view)
    }

    // как вставить значения внутри него
    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
       val shopItem=list[position]
        viewHolder.tvName.text=shopItem.name
        viewHolder.tvCount.text=shopItem.count.toString()
        viewHolder.view.setOnLongClickListener{
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //реализация вью холдера
    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }


}