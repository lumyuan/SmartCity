package com.example.smartcity.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class MyViewHolder<T : ViewBinding>(var binding: T)
    : RecyclerView.ViewHolder(binding.root)