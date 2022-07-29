package com.bignerdranch.android.shoplist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.shoplist.domain.ShopItem

/**
 * клас, необходимый для реализации CalculateDiff в другом потоке для небольшого ускорения
 */
class ShopItemDiffCallback :DiffUtil.ItemCallback<ShopItem>(){

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }

}