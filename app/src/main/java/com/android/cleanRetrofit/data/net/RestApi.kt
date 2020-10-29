package com.android.cleanRetrofit.data.net

import com.android.cleanRetrofit.data.net.models.Categories
import io.reactivex.Observable

interface RestApi {
    fun getCategories(): Observable<List<Categories>>
}