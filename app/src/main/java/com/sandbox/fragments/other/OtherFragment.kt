package com.sandbox.fragments.other

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sandbox.databinding.FragmentOtherBinding


class OtherFragment : Fragment() {

    private var _binding: FragmentOtherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtherBinding.inflate(inflater, container, false)


        with(binding){
            btnSend.setOnClickListener {
                inputMsg.text?.let {
                    if(it.isNotEmpty()){
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, it.toString())
                        val chosenIntent =
                            Intent.createChooser(intent, "Способ послать сообщение")
                        startActivity(chosenIntent)
                    }
                }
            }
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}