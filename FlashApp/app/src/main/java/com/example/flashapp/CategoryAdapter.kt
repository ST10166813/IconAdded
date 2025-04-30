package com.example.flashapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val categoryList: List<CategoryEntity>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.categoryDescriptionTextView)
        val limitTextView: TextView = itemView.findViewById(R.id.categoryLimitTextView)
        val iconImageView: ImageView = itemView.findViewById(R.id.categoryIconImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false) // Create this layout file
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categoryList[position]
        holder.nameTextView.text = currentCategory.name
        holder.descriptionTextView.text = currentCategory.description ?: "No description"
        holder.limitTextView.text = currentCategory.limit?.toString() ?: "No limit"
        holder.iconImageView.setImageResource(currentCategory.icon)
    }

    override fun getItemCount() = categoryList.size
}