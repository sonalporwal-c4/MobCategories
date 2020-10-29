package com.android.cleanRetrofit.data.cache

import com.android.cleanRetrofit.data.net.models.Categories
import io.reactivex.Observable

class InMemStoryCache constructor(private val maxAge: Long) : CategoriesCache {

    private var frontPageCache: CacheEntry<List<Categories>>? = null

    override fun getCategories(): Observable<List<Categories>> {
        return frontPageCache.toObservable()
    }

    override fun setCategories(categories: List<Categories>) {
        frontPageCache = CacheEntry(categories)
    }


    private data class CacheEntry<out T>(val item: T, val time: Long = System.currentTimeMillis())

    private fun <T> CacheEntry<T>?.toObservable(): Observable<T> {
        if (this == null || this.isStale()) {
            return Observable.empty()
        }

        return Observable.just(item)
    }

    private fun CacheEntry<*>.isStale(): Boolean {
        return System.currentTimeMillis() > time + maxAge
    }
}