package com.bignerdranch.android.shoplist.domain
/**
 * базовый  класс списка покупок
 **/
data class ShopItem(
    val id:Int,
    val name:String,
    val count: Int,
    val enabled:Boolean
)
