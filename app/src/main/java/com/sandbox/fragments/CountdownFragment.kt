package com.sandbox.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sandbox.databinding.FragmentCountdownBinding


class CountdownFragment : Fragment() {

    private var _binding: FragmentCountdownBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentCountdownBinding.inflate(inflater, container, false)

        with(binding){
            btn.setOnClickListener {
                doWork()
            }
        }



        return binding.root
    }

    private fun doWork(){



        val handler = Handler(Looper.getMainLooper())
        for(i in 0..5){
            handler.postDelayed({
                _binding?.let{
                    binding.textview.text = "${5 - i}"
                }
            }, i.toLong()*1000)
        }


//        var handler1: Handler? = null
//        var i = 5
//        handler1 = Handler(Looper.getMainLooper()){
//            binding.textview.text = i.toString()
//            i -= 1
//            if(i>0)
//                handler1?.sendEmptyMessageDelayed(0, 1000)
//            return@Handler true
//        }
//        handler1.sendEmptyMessageDelayed(0, 1000)


//        object : CountDownTimer(5100,1000){
//            override fun onTick(p0: Long) {
//                binding.textview.text = (p0/1000).toString()
//            }
//
//            override fun onFinish() {
//                binding.textview.text = "Finished!"
//            }
//
//        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

