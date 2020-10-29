package io.lundgren.cleanRetrofit.domain.repositories

import com.android.cleanRetrofit.data.net.models.Categories
import io.reactivex.Single

interface CategoryRepository {
    fun getCategories(forceFreshData: Boolean = false): Single<List<Categories>>
}