package com.android.cleanRetrofit.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.cleanRetrofit.presentation.mvp.base.BasePresenter

abstract class BaseActivity : AppCompatActivity(), PresenterCache {

    private lateinit var presenterMap: MutableMap<String, BasePresenter<*>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenterMap = getStoredOrEmptyMap()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenterMap
    }

    @Suppress("UNCHECKED_CAST")
    override fun <P : BasePresenter<V>, V> getPresenter(key: String, factory: () -> P): P {
        if (!presenterMap.containsKey(key)) {
            presenterMap[key] = factory()
        }

        return presenterMap[key] as P
    }

    @Suppress("UNCHECKED_CAST")
    private fun getStoredOrEmptyMap(): MutableMap<String, BasePresenter<*>> {
        return mutableMapOf()
    }
}