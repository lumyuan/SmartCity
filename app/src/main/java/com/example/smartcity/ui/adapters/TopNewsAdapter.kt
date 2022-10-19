package com.example.smartcity.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.activities.WatchNewsActivity
import com.example.smartcity.base.MyViewHolder
import com.example.smartcity.databinding.ItemTopNewsBinding
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.pojo.NewsBean
import com.example.smartcity.utils.FastFunctionUtils

class TopNewsAdapter(private val list: ArrayList<NewsBean>): RecyclerView.Adapter<MyViewHolder<ItemTopNewsBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<ItemTopNewsBinding> {
        return MyViewHolder(
            ItemTopNewsBinding.bind(
                View.inflate(parent.context, R.layout.item_top_news, null)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder<ItemTopNewsBinding>, position: Int) {
        val binding = holder.binding
        val newsBean = list[position]
        binding.title.text = "${newsBean.title}"
        binding.publishDate.text = "${newsBean.publishDate}"
        binding.likeNum.text = "点赞：${newsBean.likeNum}"
        Glide.with(binding.pick)
            .load("${SmartCitySettingsModel.getWorkUrl()}${newsBean.cover}")
            .into(binding.pick)
        binding.root.setOnClickListener {
            val intent = Intent(it.context, WatchNewsActivity::class.java).apply {
                putExtra("id", newsBean.id)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}