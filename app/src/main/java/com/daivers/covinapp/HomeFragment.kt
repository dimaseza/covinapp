package com.daivers.covinapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daivers.covinapp.adapter.OnClickListener
import com.daivers.covinapp.adapter.TodayNewsAdapter
import com.daivers.covinapp.adapter.TopNewsAdapter
import com.daivers.covinapp.databinding.FragmentHomeBinding
import com.daivers.covinapp.utils.PermissionsRequestor
import com.daivers.covinapp.viewModel.HomeViewModel
import com.daivers.covinapp.viewModel.HomeViewModelFactory
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapview.MapScheme

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var permissionsRequestor: PermissionsRequestor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        val activity = requireActivity()
        val viewModelFactory = HomeViewModelFactory(activity.application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

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

        binding.miniMap.onCreate(savedInstanceState)

        binding.miniMap.setOnReadyListener {
            Log.d(MainActivity.TAG, "HERE Rendering Engine attached.")
        }

        handleAndroidPermissions()
        return binding.root
    }

    private fun handleAndroidPermissions() {
        permissionsRequestor = PermissionsRequestor(requireActivity())
        permissionsRequestor.request(object : PermissionsRequestor.ResultListener {
            override fun permissionsGranted() {
                loadMapScene()
            }

            override fun permissionsDenied() {
                Log.e(MainActivity.TAG, "Permissions denied by user.")
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsRequestor.onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun loadMapScene() {
        // Load a scene from the HERE SDK to render the map with a map scheme.
        binding.miniMap.mapScene.loadScene(MapScheme.NORMAL_DAY
        ) { mapError ->
            if (mapError == null) {
                val distanceInMeters = 1000 * 10.toDouble()
                binding.miniMap.camera.lookAt(
                    GeoCoordinates(-8.17211, 113.69953), distanceInMeters
                )
            } else {
                Log.d(
                    MainActivity.TAG,
                    "Loading map failed: mapError: " + mapError.name
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.miniMap.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.miniMap.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.miniMap.onDestroy()
    }
}
