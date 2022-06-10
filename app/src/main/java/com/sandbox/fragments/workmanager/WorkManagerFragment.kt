package com.sandbox.fragments.workmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.sandbox.R
import com.sandbox.databinding.FragmentCustomViewBinding
import com.sandbox.databinding.FragmentWorkManagerBinding
import com.sandbox.fragments.customview.TrafficLights

class WorkManagerFragment : Fragment() {

    private var _binding: FragmentWorkManagerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkManagerBinding.inflate(inflater, container, false)

        binding.btnStartWorker.setOnClickListener {
            val myWorkRequest =
                OneTimeWorkRequest.Builder(MyWorker::class.java)
                    .build()
            context?.let { it1 ->
                WorkManager.getInstance(it1).enqueue(myWorkRequest)
                WorkManager.getInstance(it1)
                    .getWorkInfoByIdLiveData(myWorkRequest.id)
                    .observe(viewLifecycleOwner) {
                        if(it.state.isFinished)
                            Toast.makeText(context, "Work finished!", Toast.LENGTH_SHORT).show()
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