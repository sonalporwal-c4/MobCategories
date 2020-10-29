package com.android.cleanRetrofit.data.repositories

import com.android.cleanRetrofit.data.cache.CategoriesCache
import com.android.cleanRetrofit.data.net.RestApi
import com.android.cleanRetrofit.data.net.models.Categories
import io.lundgren.cleanRetrofit.domain.repositories.CategoryRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CachedCategoryRepository @Inject constructor(restApi: RestApi, categoriesCache: CategoriesCache): CategoryRepository {

    private val cacheOrApi: Single<List<Categories>>
    private val apiDirectly: Single<List<Categories>>

    init {
        val apiWithWriteToCache = restApi
                .getCategories()
                .doOnNext { categoriesCache.setCategories(it) }
        val fromCache = categoriesCache.getCategories()

        cacheOrApi = Observable.concat(fromCache, apiWithWriteToCache).firstOrError()
        apiDirectly = apiWithWriteToCache.firstOrError()
    }

    override fun getCategories(forceFreshData: Boolean): Single<List<Categories>> {
        if (forceFreshData) {
            return apiDirectly
        }

        return cacheOrApi
    }
}