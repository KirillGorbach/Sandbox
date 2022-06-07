package com.sandbox.fragments.room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sandbox.R
import com.sandbox.databinding.FragmentRoomBinding


class RoomFragment : Fragment() {

    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomFragmentViewModel: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)

        roomFragmentViewModel =
            ViewModelProvider(this)[RoomViewModel::class.java]

        with(binding){
            btnAdd.setOnClickListener {
                if (inputName.text!=null && inputAge.text!=null)
                    if (inputName.text!!.isNotEmpty() && inputAge.text!!.isNotEmpty()){
                        roomFragmentViewModel.addUser(
                            inputName.text.toString(),
                            inputAge.text.toString().toInt()
                        )
                    } else {
                        Toast
                            .makeText(context, "Fill the name and the age!", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
            btnDelete.setOnClickListener { roomFragmentViewModel.deleteAllUsers() }
        }

        initViewModel()

        return binding.root
    }

    private fun initViewModel(){
        roomFragmentViewModel.users.observe(viewLifecycleOwner){
            binding.tableLayout.removeAllViews()
            it.forEach { user ->
                val tableRaw: TableRow =
                    LayoutInflater.from(context).inflate(R.layout.attrib_row, null) as TableRow

                tableRaw.findViewById<TextView>(R.id.output_id).text = user.id.toString()
                tableRaw.findViewById<TextView>(R.id.output_name).text = user.name
                tableRaw.findViewById<TextView>(R.id.output_age).text = user.age.toString()
                binding.tableLayout.addView(tableRaw)
            }
            binding.tableLayout.requestLayout()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}