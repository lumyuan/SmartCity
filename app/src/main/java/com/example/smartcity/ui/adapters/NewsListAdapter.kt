package com.example.smartcity.ui.adapters

import android.annotation.SuppressLint
import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.smartcity.R
import com.example.smartcity.base.MyViewHolder
import com.example.smartcity.databinding.ItemNewsBinding
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.pojo.NewsBean
import com.example.smartcity.utils.LogUtils
import java.text.SimpleDateFormat


class NewsListAdapter(private val list: ArrayList<NewsBean>): RecyclerView.Adapter<MyViewHolder<ItemNewsBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<ItemNewsBinding> {
        return MyViewHolder(
            ItemNewsBinding.bind(
                View.inflate(parent.context, R.layout.item_news, null)
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    private val dateFloat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder<ItemNewsBinding>, position: Int) {
        val binding = holder.binding
        val newsBean = list[position]
        if (newsBean.hot != "N") {
            binding.title.text = "🔥\t"
        }
        binding.title.append(newsBean.title)
        binding.subtitle.text = newsBean.subTitle
        binding.content.text = Html.fromHtml(newsBean.content)
        val iu = "${SmartCitySettingsModel.getWorkUrl()}${newsBean.cover}"
        Glide.with(binding.cover)
            .load(iu)
            .into(binding.cover)
        binding.publishDate.text = "发布日期：${newsBean.publishDate}"
        binding.readNum.text = "${newsBean.readNum}"
        binding.commentNum.text = if (newsBean.commentNum.toString() == "null") "0" else newsBean.commentNum.toString()
        binding.likeNum.text = "${newsBean.likeNum}"
        if (position == itemCount - 1){
            binding.toBottom.visibility = View.VISIBLE
        }else {
            binding.toBottom.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}