package com.cascer.mealreceipe.ui.meallist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cascer.mealreceipe.databinding.ViewMealBinding
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.utils.ImageUtils.load

class MealAdapter(
    private val listener: (meal: Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var listItem = mutableListOf<Meal>()

    fun sendData(data: List<Meal>) {
        val diffCallback = MealDiffCallback(listItem, data)
        val diffMeal = DiffUtil.calculateDiff(diffCallback)
        listItem.clear()
        listItem.addAll(data)
        diffMeal.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            ViewMealBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        listItem[position].let { holder.bind(it) }
    }

    inner class MealViewHolder(private val binding: ViewMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.invoke(listItem[bindingAdapterPosition])
            }
        }

        fun bind(item: Meal) {
            with(binding) {
                tvMeal.text = item.strMeal
                ivMeal.load(binding.root.context, item.strMealThumb)
            }
        }
    }
}