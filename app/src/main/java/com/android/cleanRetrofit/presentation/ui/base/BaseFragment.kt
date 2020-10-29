package com.android.cleanRetrofit.presentation.ui.base

import android.os.Bundle
import android.view.View
import com.android.cleanRetrofit.presentation.mvp.base.BasePresenter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<out P : BasePresenter<V>, in V>: Fragment() {

    abstract val presenterKey: String
    abstract fun createPresenter(): P

    override fun onStart() {
        super.onStart()

        getPresenter().onViewAttached(this as V)
    }

    override fun onStop() {
        super.onStop()

        getPresenter().onViewDetach()
    }

    open fun setClickListener(){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    fun getPresenter(): P {
        return (activity as PresenterCache).getPresenter(presenterKey, this::createPresenter)
    }

    fun navigate(destination: Int) {
        findNavController().navigate(destination)
    }

}
