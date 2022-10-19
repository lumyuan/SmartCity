package com.example.smartcity.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.base.MyViewHolder
import com.example.smartcity.databinding.DialogSearchServiceBinding
import com.example.smartcity.databinding.ItemAllServiceBinding
import com.example.smartcity.databinding.ItemDialogServiceBinding
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.pojo.ServiceBean

class DialogServiceAdapter(private val list: ArrayList<ServiceBean>): RecyclerView.Adapter<MyViewHolder<ItemDialogServiceBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<ItemDialogServiceBinding> {
        return MyViewHolder(
            ItemDialogServiceBinding.bind(
                View.inflate(parent.context, R.layout.item_dialog_service, null)
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder<ItemDialogServiceBinding>, position: Int) {
        val binding = holder.binding
        val serviceBean = list[position]
        val smartCitySettingsBean = SmartCitySettingsModel.smartCitySettings.value!!
        Glide.with(binding.icon)
            .load("${smartCitySettingsBean.dns}:${smartCitySettingsBean.port}${serviceBean.imgUrl}")
            .error(R.mipmap.ic_launcher)
            .into(binding.icon)
        binding.name.text = serviceBean.serviceName
        binding.root.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}