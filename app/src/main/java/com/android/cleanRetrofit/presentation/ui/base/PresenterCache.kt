package com.android.cleanRetrofit.presentation.ui.base

import com.android.cleanRetrofit.presentation.mvp.base.BasePresenter

interface PresenterCache {
    fun <P : BasePresenter<V>, V> getPresenter(key: String, factory: () -> P): P
}