package com.sandbox.fragments.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sandbox.databinding.FragmentCustomViewBinding


class CustomViewFragment : Fragment() {

    private var _binding: FragmentCustomViewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomViewBinding.inflate(inflater, container, false)

        with(binding){
            btnGreenLight.setOnClickListener {
                viewTrafficLights.setState(TrafficLights.LightState.GREEN)
            }
            btnRedLight.setOnClickListener {
                viewTrafficLights.setState(TrafficLights.LightState.RED)
            }
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}