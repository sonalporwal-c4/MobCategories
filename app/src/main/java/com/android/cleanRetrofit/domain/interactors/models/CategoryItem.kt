package com.android.cleanRetrofit.domain.interactors.models

import com.android.cleanRetrofit.data.net.models.Product

data class CategoryItem(
        val id: Long,
        val name: String,
        val description: String?,
        val products: List<Product>
)
