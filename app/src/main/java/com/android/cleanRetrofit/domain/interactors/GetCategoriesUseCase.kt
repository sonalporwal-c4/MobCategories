package com.android.cleanRetrofit.domain.interactors

import com.android.cleanRetrofit.data.net.models.Categories
import com.android.cleanRetrofit.data.net.models.Product
import io.lundgren.cleanRetrofit.domain.ResultScheduler
import io.lundgren.cleanRetrofit.domain.WorkScheduler
import com.android.cleanRetrofit.domain.interactors.models.CategoryItem
import io.lundgren.cleanRetrofit.domain.repositories.CategoryRepository
import io.reactivex.Scheduler
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
        val storyRepository: CategoryRepository,
        @WorkScheduler workScheduler: Scheduler,
        @ResultScheduler resultScheduler: Scheduler
) : BaseUseCase<List<CategoryItem>>(workScheduler, resultScheduler){

    fun execute(onSuccess: (List<CategoryItem>) -> Unit, onError: (Throwable) -> Unit = onErrorStub, forceFresh: Boolean = false) {
        storyRepository
                .getCategories(forceFresh)
                .map { categoriesToItems(it) }
                .executeUseCase(onSuccess, onError)
    }

    private fun categoriesToItems(stories: List<Categories>): List<CategoryItem> {
        return stories.map {
            CategoryItem(
                    it.id,
                    it.name,
                    it.description,
                    productsToItems(it.products)
            )
        }
    }

    private fun productsToItems(items: List<Product>): List<Product> {
        return items.map {
            Product(
                    it.id,
                    it.categoryId,
                    it.name,
                    "http://mobcategories.s3-website-eu-west-1.amazonaws.com"+it.url,
                    it.description,
                    it.salePrice
            )
        }
    }

}