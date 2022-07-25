package com.sandbox.fragments.paging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sandbox.R
import com.sandbox.fragments.paging.model.Name

class NameAdapter:
    PagingDataAdapter<Name, NameAdapter.NameViewHolder>(NameComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NameViewHolder {
        return NameViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.paging_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        if (item != null) {
            holder.text.text = item.name
        }
    }

    class NameViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text: TextView = view.findViewById(R.id.text)
    }

    object NameComparator : DiffUtil.ItemCallback<Name>() {
        override fun areItemsTheSame(oldItem: Name, newItem: Name): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Name, newItem: Name): Boolean {
            return oldItem == newItem
        }
    }
}
