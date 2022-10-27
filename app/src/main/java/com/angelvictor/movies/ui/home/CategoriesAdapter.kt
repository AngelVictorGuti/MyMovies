package com.angelvictor.movies.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.ViewCategoryBinding
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.inflate
import com.angelvictor.movies.ui.common.toResource

class CategoriesAdapter(
    private var categories: List<Category>,
    private val onClickListener: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    fun updateCategories(newCategories: List<Category>) {
        this.categories = newCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_category, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
        holder.itemView.setOnClickListener { onClickListener(category) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewCategoryBinding.bind(view)
        fun bind(category: Category) {
            binding.categoryTitle.text = binding.categoryTitle.context.getText(category.toResource())
        }
    }

    override fun getItemCount(): Int = categories.size
}