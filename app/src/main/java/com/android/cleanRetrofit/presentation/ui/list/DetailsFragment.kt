package com.android.cleanRetrofit.presentation.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.cleanRetrofit.R
import com.android.cleanRetrofit.data.net.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment: Fragment() {

    var productItem: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productItem = arguments?.get("productItem") as Product?
        Log.d("sonal","product"+productItem.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name_text.text = productItem!!.name
        Picasso.get().load(productItem!!.url).into(food_img)
        amount_text.text = productItem!!.salePrice.amount.toString()
        currency.text = productItem!!.salePrice.currency
    }


}