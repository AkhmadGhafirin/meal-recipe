package com.cascer.mealreceipe.ui.meallist

import androidx.recyclerview.widget.DiffUtil
import com.cascer.mealreceipe.domain.model.Meal

class MealDiffCallback(
    private val oldList: List<Meal>,
    private val newList: List<Meal>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idMeal == newList[newItemPosition].idMeal
    }
}