package com.cascer.mealreceipe.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cascer.mealreceipe.databinding.ViewCategoryBinding
import com.cascer.mealreceipe.domain.model.Category
import com.cascer.mealreceipe.utils.ImageUtils.load
import com.cascer.mealreceipe.utils.gone
import com.cascer.mealreceipe.utils.visible

class CategoryAdapter(
    private val listener: (category: Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val listItem = mutableListOf<Category>()
    private var selected = RecyclerView.NO_POSITION

    fun sendData(data: List<Category>) {
        val diffCallback = CategoryDiffCallback(listItem, data)
        val diffCategory = DiffUtil.calculateDiff(diffCallback)
        listItem.clear()
        listItem.addAll(data)
        diffCategory.dispatchUpdatesTo(this)
    }

    fun setSelected(name: String) {
        val index = listItem.indexOf(listItem.find { it.strCategory == name })
        notifyItemChanged(selected)
        selected = index
        listener.invoke(listItem[selected])
        notifyItemChanged(selected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ViewCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        listItem[position].let { holder.bind(it, position) }
    }

    inner class CategoryViewHolder(private val binding: ViewCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                notifyItemChanged(selected)
                selected = bindingAdapterPosition
                listener.invoke(listItem[selected])
                notifyItemChanged(selected)
            }
        }

        fun bind(item: Category, position: Int) {
            with(binding) {
                if (selected == position) {
                    ivSelected.visible()
                } else {
                    ivSelected.gone()
                }
                tvCategory.text = item.strCategory
                ivCategory.load(binding.root.context, item.strCategoryThumb)
            }
        }
    }
}