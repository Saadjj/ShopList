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
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? =null
//и это
    var onShopItemClickListener: ((ShopItem) -> Unit)? =null

    // как создать вью
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        //проверка вьютайпа и выставление  нужного слоя
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("UnknownViewType:$viewType")
        }

        val view =
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    // как вставить значения внутри него(привязка)
    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        //а вообще происходит установка слушателей кликов(читай наблюдателей)
        //эт тож моя шляпа
        viewHolder.view.setOnClickListener{
            onShopItemClickListener?.invoke(shopItem)
            true       }
        viewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
    }

    //определение типа вьютайпа
    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
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

    //создается ибо не стоит напрямую вызывать из адаптреа вью модель
    interface OnShopItemLongClickListener {
        fun onShopItemLongClick(shopItem: ShopItem)
    }

    //моя бурда
    interface OnShopItemClickListener {
        fun onShopItemClickListener(shopItem: ShopItem)
    }


    //ициализация дополнительных переменных
    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1

        const val MAX_POOL_SIZE = 15
    }
}