package com.example.smartcity.ui.adapters

import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.activities.MainActivity
import com.example.smartcity.activities.NavigationActivity
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.pojo.NavigationPictureBean
import com.example.smartcity.utils.LogUtils
import com.example.smartcity.utils.ToastUtil
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * 导航页轮播图适配器
 */
class SimpleGlideLoaderAdapter(list: MutableList<NavigationPictureBean>): BannerImageAdapter<NavigationPictureBean>(list) {
    override fun onBindView(holder: BannerImageHolder, data: NavigationPictureBean, position: Int, size: Int) {
        Glide.with(holder.itemView)
            .load("${SmartCitySettingsModel.getWorkUrl()}${data.advImg}")
            .into(holder.imageView)
        LogUtils.print(data)
    }
}