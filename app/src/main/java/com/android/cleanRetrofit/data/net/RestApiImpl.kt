package io.lundgren.cleanRetrofit.data.net

import com.android.cleanRetrofit.data.net.ProductService
import com.android.cleanRetrofit.data.net.RestApi
import com.android.cleanRetrofit.data.net.models.Categories
import io.reactivex.Observable
import javax.inject.Inject

class RestApiImpl @Inject constructor(private val productService: ProductService) : RestApi {

    override fun getCategories(): Observable<List<Categories>> {
        return productService.getCategories()
    }

}