package com.android.cleanRetrofit.presentation.di

import dagger.Module
import dagger.Provides
import com.android.cleanRetrofit.data.cache.CategoriesCache
import com.android.cleanRetrofit.data.cache.InMemStoryCache
import com.android.cleanRetrofit.data.net.ProductService
import com.android.cleanRetrofit.data.net.RestApi
import io.lundgren.cleanRetrofit.data.net.RestApiImpl
import com.android.cleanRetrofit.data.repositories.CachedCategoryRepository
import io.lundgren.cleanRetrofit.domain.ResultScheduler
import io.lundgren.cleanRetrofit.domain.WorkScheduler
import io.lundgren.cleanRetrofit.domain.repositories.CategoryRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @WorkScheduler
    fun providesWorkScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Singleton
    @ResultScheduler
    fun providesResultScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    fun providesCategoriesCache(): CategoriesCache {
        return InMemStoryCache(5 * 60 * 60 * 1000)
    }

    @Provides
    @Singleton
    fun providesRestApi(productService: ProductService): RestApi {
        return RestApiImpl(productService)
    }

    @Provides
    @Singleton
    fun providesCategoryRepository(restApi: RestApi, storyCache: CategoriesCache): CategoryRepository {
        return CachedCategoryRepository(restApi, storyCache)
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://mobcategories.s3-website-eu-west-1.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }
}