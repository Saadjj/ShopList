package com.bignerdranch.android.shoplist.domain
/**
 * базовый  класс списка покупок
 **/
data class ShopItem(
    val name:String,
    val count: Int,
    val enabled:Boolean,
    var id:Int= UNDEFINED_ID
)
{
    companion object{
        const val UNDEFINED_ID=-1
    }
}
