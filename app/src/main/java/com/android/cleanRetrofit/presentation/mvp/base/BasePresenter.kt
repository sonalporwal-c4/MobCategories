package com.android.cleanRetrofit.presentation.mvp.base

interface BasePresenter<in V> {
    fun onViewAttached(view: V)
    fun onViewDetach()
}