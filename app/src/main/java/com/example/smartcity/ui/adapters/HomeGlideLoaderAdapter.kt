package com.example.smartcity.ui.adapters

import com.bumptech.glide.Glide
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.pojo.NavigationPictureBean
import com.example.smartcity.utils.ToastUtil
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * 导航页轮播图适配器
 */
class HomeGlideLoaderAdapter(list: MutableList<NavigationPictureBean>): BannerImageAdapter<NavigationPictureBean>(list) {
    override fun onBindView(holder: BannerImageHolder, data: NavigationPictureBean, position: Int, size: Int) {
        Glide.with(holder.itemView)
            .load("${SmartCitySettingsModel.getWorkUrl()}${data.advImg}")
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            val servModule = data.servModule.toString()
            ToastUtil.toast(servModule)
        }
    }
}