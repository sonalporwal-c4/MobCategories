package com.android.cleanRetrofit.data.net.models

import java.io.Serializable

data class Categories(
        val id: Long,
        val name: String,
        val description: String?,
        val products: List<Product>
):Serializable

data class Product(
        val id: Long,
        val categoryId: Int,
        val name: String,
        val url: String,
        val description: String?,
        val salePrice: SalePrice
): Serializable

data class SalePrice(
        val amount: Double,
        val currency: String
):Serializable