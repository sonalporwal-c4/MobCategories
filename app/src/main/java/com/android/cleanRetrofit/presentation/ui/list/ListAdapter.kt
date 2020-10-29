package com.android.cleanRetrofit.presentation.ui.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.cleanRetrofit.R
import com.android.cleanRetrofit.data.net.models.Product
import com.squareup.picasso.Picasso

enum class RowType {
    HEADER,
    ROW
}

class ListAdapter(private val dataset: MutableList<RecyclerViewContainer>) : RecyclerView.Adapter<ListAdapter.CategoryViewHolder>() {

    lateinit var  inflatedView : View

     var onItemClick: ((Product) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        inflatedView = when (viewType) {
            RowType.ROW.ordinal -> layoutInflater.inflate(R.layout.list_item_category, parent,false)
            else -> layoutInflater.inflate(R.layout.header_view, parent,false)
        }
        return CategoryViewHolder(inflatedView)
    }

    override fun getItemCount() = dataset.size
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val item = dataset[position]
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        if (dataset[position].isHeader) {
            return 0
        }else {
            return 1
        }
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var headerView: TextView? = null
        private var textView: TextView? = null
        private var imageView: ImageView? = null

        init {
            headerView = itemView.findViewById(R.id.header_text_view)
            textView = itemView.findViewById(R.id.text_view)
            imageView = itemView.findViewById(R.id.image_view)

        }

        fun bind(item : RecyclerViewContainer) {
            if (item.isHeader) {
                headerView!!.text = item.headerTitle
            }else {
                textView!!.text = item.product!!.name
                Picasso.get().load(item.product!!.url).into(imageView)

            }
            itemView.setOnClickListener {
                onItemClick?.invoke(item.product!!)
            }
        }

    }

}
