package com.example.foodbookapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.foodbookapplication.R
import com.example.foodbookapplication.base.BaseFragment
import com.example.foodbookapplication.databinding.FragmentFoodDetailBinding
import com.example.foodbookapplication.util.doPlaceholder
import com.example.foodbookapplication.util.downloadImage
import com.example.foodbookapplication.viewmodel.FoodDetailViewModel


class FoodDetailFragment :
    BaseFragment<FragmentFoodDetailBinding>(FragmentFoodDetailBinding::inflate) {

    private val args: FoodDetailFragmentArgs by navArgs()

    private var besinId: Int = 0

    private lateinit var viewModel: FoodDetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            FoodDetailFragmentArgs.fromBundle(it).besinId
            Log.e("besinId", besinId.toString())
        }

        viewModel = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.takeRoomData(args.besinId)
        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.foodLiveData.observe(viewLifecycleOwner, { food ->
            food?.let {
                binding.txtFoodName.text = it.isim
                binding.txtFoodCalorie.text = it.kalori
                binding.txtFoodCarbohydrate.text = it.karbonhidrat
                binding.txtFoodOil.text = it.yag
                binding.txtFoodProtein.text = it.protein
                context?.let { context ->
                    binding.imgFood.downloadImage(food.gorsel, doPlaceholder(context))
                }
            }
        })
    }
}