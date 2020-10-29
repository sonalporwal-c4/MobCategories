package com.android.cleanRetrofit.data.repositories

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.android.cleanRetrofit.data.cache.CategoriesCache
import com.android.cleanRetrofit.data.net.RestApi
import com.android.cleanRetrofit.data.net.models.*
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import java.util.*

class CachedCategoryRepositoryTest {

    @Test
    fun getCategories() {
        // given:
        val fromCache = generateStoryList()
        val fromApi = generateStoryList()
        val cache = mock<CategoriesCache> {
            on { getCategories() } doReturn Observable.just(fromCache)
        }
        val api = mock<RestApi> {
            on { getCategories() } doReturn Observable.just(fromApi)
        }
        val storyRepository = CachedCategoryRepository(api, cache)
        val testObserver = TestObserver<List<Categories>>()

        // when:
        storyRepository.getCategories().subscribeWith(testObserver)

        // then:
        testObserver.assertResult(fromCache)
    }

    @Test
    fun getCategories_takeFromCacheNoApi() {
        // given:
        val fromCache = generateStoryList()
        val cache = mock<CategoriesCache> {
            on { getCategories() } doReturn Observable.just(fromCache)
        }
        val api = mock<RestApi> {
            on { getCategories() } doReturn Observable.just(generateStoryList()).doOnNext { throw RuntimeException() }
        }
        val storyRepository = CachedCategoryRepository(api, cache)

        // when:
        val testObserver = storyRepository.getCategories().test()

        // then:
        testObserver.assertResult(fromCache)
        testObserver.assertNoErrors()
    }

    @Test
    fun getCategories_takeFromApiWhenCacheReturnsEmpty() {
        // given:
        val cache = mock<CategoriesCache> {
            on { getCategories() } doReturn Observable.empty()
        }
        val fromApi = generateStoryList()
        val api = mock<RestApi> {
            on { getCategories() } doReturn Observable.just(fromApi)
        }
        val storyRepository = CachedCategoryRepository(api, cache)

        // when:
        val testObserver = storyRepository.getCategories().test()

        // then:
        testObserver.assertResult(fromApi)
        testObserver.assertNoErrors()
    }

    @Test
    fun getCategories_storeInCacheAfterApi() {
        // given:
        val cache = mock<CategoriesCache> {
            on { getCategories() } doReturn Observable.empty()
        }
        val fromApi = generateStoryList()
        val api = mock<RestApi> {
            on { getCategories() } doReturn Observable.just(fromApi)
        }
        val storyRepository = CachedCategoryRepository(api, cache)

        // when:
        storyRepository.getCategories().test()

        // then:
        verify(cache).setCategories(fromApi)
    }

    private fun generateStoryList(): List<Categories> {
        val products = listOf(Product(1,1,"user","/bread.jpg","",SalePrice(2.00,"EURO")))
        return listOf(Categories(Random().nextLong(), "title", "url", products = products))
    }
}