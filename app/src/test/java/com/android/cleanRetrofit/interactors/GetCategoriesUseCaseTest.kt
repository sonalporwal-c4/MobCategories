package com.android.cleanRetrofit.domain.interactors

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.android.cleanRetrofit.data.net.models.*
import com.android.cleanRetrofit.domain.interactors.models.*
import io.lundgren.cleanRetrofit.domain.repositories.CategoryRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.CompletableFuture

class GetCategoriesUseCaseTest {

    @Test
    fun execute() {
        // given:
        val productsItem = listOf(Product(1,1,"user","/bread.jpg","",SalePrice(2.00,"EURO")))
        val category = Categories(123, "title", "url", productsItem)
        val expected = CategoryItem(123, category.name, category.description, productsItem)
        val repo = mock<CategoryRepository> {
            on { getCategories(any()) } doReturn Single.just(listOf(category))
        }
        val useCase = GetCategoriesUseCase(repo, Schedulers.single(), Schedulers.single())
        val future = CompletableFuture<List<CategoryItem>>()

        // when:
        useCase.execute({ future.complete(it) })

        // then:
        assertEquals(future.get(), listOf(expected))
    }

    @Test
    fun execute_forceFreshData() {
        // given:
        val repo = mock<CategoryRepository> {
            on { getCategories(any()) } doReturn Single.just(emptyList())
        }
        val useCase = GetCategoriesUseCase(repo, Schedulers.single(), Schedulers.single())

        // when:
        useCase.execute({ }, forceFresh = true)

        // then:
        verify(repo).getCategories(true)
    }

    @Test
    fun execute_onErrorOnException() {
        // given:
        val exception = RuntimeException("Some error")
        val repo = mock<CategoryRepository> {
            on { getCategories(any()) } doReturn Single.error(exception)
        }
        val useCase = GetCategoriesUseCase(repo, Schedulers.single(), Schedulers.single())
        val future = CompletableFuture<Throwable>()

        // when:
        useCase.execute({}, { future.complete(it) })

        // then:
        assertEquals(future.get(), exception)
    }
}