package com.bignerdranch.android.shoplist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.shoplist.domain.ShopItem

/**
 * адаптер, умеющий орпделять что изменилость в прилетевшем ему списке самостоятельно
 */
class ShopListDiffCallback(
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>
) : DiffUtil.Callback() {
    /**
     * возвращает колл-во элментов в старом списке
     */

    override fun getOldListSize(): Int {
        return oldList.size
    }

    /**
     * возвращает колл-во элментов в новом списке
     */
    override fun getNewListSize(): Int {
        return newList.size
    }

    /**
     * проверяет одинаковость позиции
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    /**
     * проверяет одинаковость полей элементов
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem==newItem
    }
}