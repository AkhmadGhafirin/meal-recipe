package com.cascer.mealreceipe.ui.filter

import androidx.recyclerview.widget.DiffUtil
import com.cascer.mealreceipe.domain.model.Category

class CategoryDiffCallback(
    private val oldList: List<Category>,
    private val newList: List<Category>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idCategory == newList[newItemPosition].idCategory
    }
}