package com.android.cleanRetrofit.data.net

import com.android.cleanRetrofit.data.net.models.Categories
import io.reactivex.Observable
import retrofit2.http.GET

interface ProductService {

    @GET("http://mobcategories.s3-website-eu-west-1.amazonaws.com")
    fun getCategories(): Observable<List<Categories>>

}