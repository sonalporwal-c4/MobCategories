package io.lundgren.cleanRetrofit.presentation.mvp.list

import com.nhaarman.mockito_kotlin.*
import com.android.cleanRetrofit.data.net.models.*
import com.android.cleanRetrofit.domain.interactors.GetCategoriesUseCase
import com.android.cleanRetrofit.domain.interactors.models.*
import com.android.cleanRetrofit.presentation.mvp.list.CategoryListPresenter
import com.android.cleanRetrofit.presentation.mvp.list.CategoryListView
import org.junit.Test

class CategoryListPresenterTest {

    @Test
    fun onViewAttached() {
        // given:
        val someStories = listOf(randomStoryItem())
        val onSuccess = argumentCaptor<(List<CategoryItem>) -> Unit>()
        val useCase = mock<GetCategoriesUseCase>()
        val view = mock<CategoryListView>()
        val inOrder = inOrder(view)
        val presenter = CategoryListPresenter(useCase)

        // when:
        presenter.onViewAttached(view)
        verify(useCase).execute(onSuccess.capture(), any(), eq(false))
        onSuccess.firstValue(someStories)

        // then:
        inOrder.verify(view).displayLoading()
        inOrder.verify(view).hideLoading()
        inOrder.verify(view).displayStories(someStories)
    }

    @Test
    fun onViewAttached_onErrorIsCalledWhenFetchError() {
        // given:
        val onError = argumentCaptor<(Throwable) -> Unit>()
        val useCase = mock<GetCategoriesUseCase>()
        val view = mock<CategoryListView>()
        val inOrder = inOrder(view)
        val presenter = CategoryListPresenter(useCase)

        // when:
        presenter.onViewAttached(view)
        verify(useCase).execute(any(), onError.capture(), eq(false))
        onError.firstValue(Throwable())

        // then:
        inOrder.verify(view).displayLoading()
        inOrder.verify(view).hideLoading()
        inOrder.verify(view).displayError()
    }

    @Test
    fun onViewDetach() {
        // given:
        val useCase = mock<GetCategoriesUseCase>()
        val view = mock<CategoryListView>()
        val presenter = CategoryListPresenter(useCase)

        presenter.onViewAttached(view)
        reset(view)

        // when:
        presenter.onViewDetach()

        // then:
        verify(useCase).dispose()
    }

    @Test
    fun onForceFetch() {
        // given:
        val someStories = listOf(randomStoryItem())
        val onSuccess = argumentCaptor<(List<CategoryItem>) -> Unit>()
        val useCase = mock<GetCategoriesUseCase>()
        val view = mock<CategoryListView>()
        val inOrder = inOrder(view)
        val presenter = CategoryListPresenter(useCase)

        presenter.onViewAttached(view)
        reset(view)

        // when:
        presenter.onForceFetch()
        verify(useCase).execute(onSuccess.capture(), any(), eq(true))
        onSuccess.firstValue(someStories)

        // then:
        inOrder.verify(view).displayLoading()
        inOrder.verify(view).hideLoading()
        inOrder.verify(view).displayStories(someStories)
    }

    @Test
    fun onForceFetch_onErrorIsCalledWhenFetchError() {
        // given:
        val onError = argumentCaptor<(Throwable) -> Unit>()
        val useCase = mock<GetCategoriesUseCase>()
        val view = mock<CategoryListView>()
        val inOrder = inOrder(view)
        val presenter = CategoryListPresenter(useCase)

        presenter.onViewAttached(view)
        reset(view)

        // when:
        presenter.onForceFetch()
        verify(useCase).execute(any(), onError.capture(), eq(true))
        onError.firstValue(Throwable())

        // then:
        inOrder.verify(view).displayLoading()
        inOrder.verify(view).hideLoading()
        inOrder.verify(view).displayError()
    }

    private fun randomStoryItem(): CategoryItem {
        val products = listOf(Product(1,1,"user","/bread.jpg","", SalePrice(2.00,"EURO")))
        return CategoryItem(1234, "user", "", products)
    }
}