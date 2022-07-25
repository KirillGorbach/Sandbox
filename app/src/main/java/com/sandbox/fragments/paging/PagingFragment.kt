package com.sandbox.fragments.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sandbox.databinding.FragmentPagingBinding
import com.sandbox.fragments.paging.adapter.NameAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PagingFragment : Fragment() {

    private var _binding: FragmentPagingBinding? = null
    private val binding get() = _binding!!

    private lateinit var pagingFragmentViewModel: PagingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPagingBinding.inflate(inflater, container, false)

        pagingFragmentViewModel =
            ViewModelProvider(this)[PagingViewModel::class.java]

        val pagingAdapter = NameAdapter()

        with(binding.recyclerViewPaging){
            layoutManager = LinearLayoutManager(context)
            adapter = pagingAdapter
        }
        lifecycleScope.launch{
            pagingFragmentViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}