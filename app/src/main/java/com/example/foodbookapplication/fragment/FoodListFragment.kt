package com.example.foodbookapplication.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodbookapplication.R
import com.example.foodbookapplication.adapter.FoodRecyclerAdapter
import com.example.foodbookapplication.base.BaseFragment
import com.example.foodbookapplication.databinding.FragmentFoodListBinding
import com.example.foodbookapplication.viewmodel.FoodListViewModel


class FoodListFragment : BaseFragment<FragmentFoodListBinding>(FragmentFoodListBinding::inflate) {

    private lateinit var viewModel: FoodListViewModel
    private val recyclerFoodAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        editRecyclerView()
        editingSwipeRefresh()
        observeLiveData()
    }

    private fun editRecyclerView() {
        binding.rvFoodList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerFoodAdapter
        }
    }

    private fun editingSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.txtErrorMessage.visibility = View.GONE
            binding.rvFoodList.visibility = View.GONE
            viewModel.refreshFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeLiveData() {

        viewModel.foods.observe(viewLifecycleOwner, Observer { foods ->
            foods?.let {
                binding.rvFoodList.visibility = View.VISIBLE
                recyclerFoodAdapter.updateFoodList(foods)
            }
        })

        viewModel.foodErrorMessage.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it) {
                    binding.txtErrorMessage.visibility = View.VISIBLE
                    binding.rvFoodList.visibility = View.GONE
                } else {
                    binding.txtErrorMessage.visibility = View.GONE
                }
            }
        })

        viewModel.foodLoadingProgressBar.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvFoodList.visibility = View.GONE
                    binding.txtErrorMessage.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

    }
}