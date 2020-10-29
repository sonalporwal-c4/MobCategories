package com.android.cleanRetrofit.presentation.mvp.list

import com.android.cleanRetrofit.domain.interactors.models.CategoryItem

interface CategoryListView {
    fun displayStories(categories: List<CategoryItem>)
    fun displayLoading()
    fun hideLoading()
    fun displayError()
}