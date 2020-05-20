package com.daivers.covinapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daivers.covinapp.databinding.FragmentMapsBinding
import com.daivers.covinapp.utils.PermissionsRequestor
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapview.MapScheme

/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var permissionsRequestor: PermissionsRequestor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapsBinding.inflate(inflater)

        binding.mapFull.onCreate(savedInstanceState)

        binding.mapFull.setOnReadyListener {
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
        binding.mapFull.mapScene.loadScene(
            MapScheme.NORMAL_DAY
        ) { mapError ->
            if (mapError == null) {
                val distanceInMeters = 1000 * 10.toDouble()
                binding.mapFull.camera.lookAt(
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
        binding.mapFull.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.mapFull.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapFull.onDestroy()
    }

}
