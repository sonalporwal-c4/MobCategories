package com.android.cleanRetrofit.presentation.di

import dagger.Component
import com.android.cleanRetrofit.presentation.mvp.list.CategoryListPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun createStoryListPresenter(): CategoryListPresenter

}