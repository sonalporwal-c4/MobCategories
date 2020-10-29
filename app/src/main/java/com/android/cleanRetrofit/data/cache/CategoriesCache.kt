package com.android.cleanRetrofit.data.cache

import com.android.cleanRetrofit.data.net.models.Categories
import io.reactivex.Observable

interface CategoriesCache {
    fun getCategories(): Observable<List<Categories>>
    fun setCategories(categories: List<Categories>)
}