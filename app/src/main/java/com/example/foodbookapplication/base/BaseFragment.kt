package com.example.foodbookapplication.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<out WDB : ViewBinding>(
    private val inflaterBlock: (LayoutInflater, ViewGroup?, Boolean) -> WDB
) : Fragment() {

    //    protected abstract val viewModel: ViewModel
    private var _binding: WDB? = null
    protected val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return _binding?.root ?: run {
            _binding = inflaterBlock.invoke(layoutInflater, container, false)
            binding.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected inline fun viewBinding(action: WDB.() -> Unit) {
        action(binding)
    }
}