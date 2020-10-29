package com.android.cleanRetrofit.presentation.ui.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.cleanRetrofit.R
import com.android.cleanRetrofit.data.net.models.Product
import com.android.cleanRetrofit.domain.interactors.models.CategoryItem
import com.android.cleanRetrofit.presentation.App
import com.android.cleanRetrofit.presentation.mvp.list.CategoryListPresenter
import com.android.cleanRetrofit.presentation.mvp.list.CategoryListView
import com.android.cleanRetrofit.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_story_list.*

class CategoryListFragment : BaseFragment<CategoryListPresenter, CategoryListView>(), CategoryListView {

    override val presenterKey = "storyPresenter"
    override fun createPresenter() = App.appComponent.createStoryListPresenter()

    private var viewAdapter: ListAdapter = ListAdapter(mutableListOf<RecyclerViewContainer>())
    private var itemList = mutableListOf<RecyclerViewContainer>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_story_list, container, false)
        val swipeRefresh : SwipeRefreshLayout = v.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener { getPresenter().onForceFetch() }
        return v
    }

    override fun displayStories(categories: List<CategoryItem>) {
        setData(categories)
        setRecyclerView()
    }

    private fun setData(categories: List<CategoryItem>) { // please use loop statement for your dataset, this is just a sample of how to manage very simple data.
        itemList.clear()
        for (category in categories) {
            val categoryProduct = category.products
            val category = RecyclerViewContainer(null, true, category.name)
            itemList.add(category)
            for (product in categoryProduct) {
                val product = RecyclerViewContainer(product, false, null)
                itemList.add(product)
            }
        }
    }

    private fun setRecyclerView() {
       viewAdapter = ListAdapter(itemList)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = viewAdapter
            visibility = View.VISIBLE
        }
        viewAdapter.onItemClick = {

            val bundle = bundleOf(
                    "productItem" to it)
            findNavController().navigate(R.id.action_profileFragment_to_detailFragment, bundle)
        }
    }

    override fun displayLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun displayError() {
        Toast.makeText(context, R.string.fetch_error_msg, Toast.LENGTH_LONG).show()
    }
}

class RecyclerViewContainer (var product: Product?, var isHeader: Boolean, var headerTitle: String?)