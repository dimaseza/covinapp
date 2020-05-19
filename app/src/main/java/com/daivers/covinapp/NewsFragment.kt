package com.daivers.covinapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daivers.covinapp.adapter.OnClickListener
import com.daivers.covinapp.adapter.TodayNewsAdapter
import com.daivers.covinapp.adapter.TopNewsAdapter
import com.daivers.covinapp.databinding.FragmentNewsBinding
import com.daivers.covinapp.viewModel.HomeViewModel
import com.daivers.covinapp.viewModel.HomeViewModelFactory
import com.daivers.covinapp.viewModel.NewsViewModel
import com.daivers.covinapp.viewModel.NewsViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater)
        val activity = requireActivity()
        val viewModelFactory = NewsViewModelFactory(activity.application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvTopNews.adapter = TopNewsAdapter(OnClickListener {
            viewModel.displaySelectedTopNews(it)
        })
        binding.rvTodayNews.adapter = TodayNewsAdapter(OnClickListener {
            viewModel.displaySelectedTodayNews(it)
        })
        binding.rvTopNews.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        return binding.root
    }

}
