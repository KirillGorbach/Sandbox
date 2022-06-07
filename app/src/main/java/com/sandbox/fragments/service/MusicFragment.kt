package com.sandbox.fragments.service

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sandbox.databinding.FragmentMusicBinding
import com.sandbox.fragments.service.MyService


class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)

        with(binding){
            btnStart.setOnClickListener {
                activity?.startService(Intent(activity, MyService::class.java))
            }
            btnStop.setOnClickListener {
                activity?.stopService(Intent(activity, MyService::class.java))
            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}