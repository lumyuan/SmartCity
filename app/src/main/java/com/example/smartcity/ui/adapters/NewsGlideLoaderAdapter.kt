package com.example.smartcity.ui.adapters

import com.bumptech.glide.Glide
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.pojo.NavigationPictureBean
import com.example.smartcity.net.pojo.NewsBean
import com.example.smartcity.utils.ToastUtil
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * 导航页轮播图适配器
 */
class NewsGlideLoaderAdapter(list: MutableList<NewsBean>): BannerImageAdapter<NewsBean>(list) {
    override fun onBindView(holder: BannerImageHolder, data: NewsBean, position: Int, size: Int) {
        Glide.with(holder.itemView)
            .load("${SmartCitySettingsModel.getWorkUrl()}${data.cover}")
            .into(holder.imageView)
        holder.itemView.setOnClickListener {

        }
    }
}