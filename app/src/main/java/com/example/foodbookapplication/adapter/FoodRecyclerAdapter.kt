package com.example.foodbookapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbookapplication.databinding.FoodRecyclerRowBinding
import com.example.foodbookapplication.fragment.FoodListFragmentDirections
import com.example.foodbookapplication.model.Food
import com.example.foodbookapplication.util.doPlaceholder
import com.example.foodbookapplication.util.downloadImage

class FoodRecyclerAdapter(
    private val foodList: ArrayList<Food>
) :
    RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {

    inner class FoodViewHolder constructor(val binding: FoodRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FoodRecyclerRowBinding.inflate(inflater, parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount() = foodList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.foodName.text = foodList[position].isim
        holder.binding.foodCalorie.text = foodList[position].kalori


        holder.itemView.setOnClickListener {
            val action =
                FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.downloadImage(
            foodList[position].gorsel,
            doPlaceholder(holder.itemView.context)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFoodList(newFoodList: List<Food>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

}