package com.android.cleanRetrofit.presentation.mvp.list

import com.android.cleanRetrofit.domain.interactors.GetCategoriesUseCase
import com.android.cleanRetrofit.domain.interactors.models.CategoryItem
import com.android.cleanRetrofit.presentation.mvp.base.BasePresenter
import javax.inject.Inject

class CategoryListPresenter @Inject constructor(val getCategories: GetCategoriesUseCase) : BasePresenter<CategoryListView> {

    var view: CategoryListView? = null

    override fun onViewAttached(view: CategoryListView) {
        this.view = view

        this.view?.displayLoading()
        getCategories.execute(this::onFrontPageData, this::onFrontPageError)
    }

    override fun onViewDetach() {
        getCategories.dispose()
        view = null
    }

    fun onForceFetch() {
        view!!.displayLoading()
        getCategories.execute(this::onFrontPageData, this::onFrontPageError, true)
    }

    private fun onFrontPageData(categories: List<CategoryItem>) {
        view!!.hideLoading()
        view!!.displayStories(categories)
    }

    private fun onFrontPageError(ex: Throwable) {
        view!!.hideLoading()
        view!!.displayError()
    }
}