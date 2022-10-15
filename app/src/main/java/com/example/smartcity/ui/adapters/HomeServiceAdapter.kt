package com.example.smartcity.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.base.MyViewHolder
import com.example.smartcity.databinding.ItemHomeServiceBinding
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.pojo.ServiceBean

class HomeServiceAdapter(private val list: ArrayList<ServiceBean>): RecyclerView.Adapter<MyViewHolder<ItemHomeServiceBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<ItemHomeServiceBinding> {
        return MyViewHolder(
            ItemHomeServiceBinding.bind(
                View.inflate(parent.context, R.layout.item_home_service, null)
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder<ItemHomeServiceBinding>, position: Int) {
        val binding = holder.binding
        val serviceBean = list[position]
        val smartCitySettingsBean = SmartCitySettingsModel.smartCitySettings.value!!
        Glide.with(binding.icon)
            .load("${smartCitySettingsBean.dns}:${smartCitySettingsBean.port}${serviceBean.imgUrl}")
            .error(R.mipmap.ic_launcher)
            .into(binding.icon)
        binding.title.text = serviceBean.serviceName
    }

    override fun getItemCount(): Int {
        return list.size
    }
}