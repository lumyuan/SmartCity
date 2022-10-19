package com.example.smartcity.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcity.R
import com.example.smartcity.base.MyViewHolder
import com.example.smartcity.databinding.ItemCommentBinding
import com.example.smartcity.net.pojo.CommentBean

class CommentAdapter(): RecyclerView.Adapter<MyViewHolder<ItemCommentBinding>>() {

    private val list = ArrayList<CommentBean>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<ItemCommentBinding> {
        return MyViewHolder(
            ItemCommentBinding.bind(
                View.inflate(parent.context, R.layout.item_comment, null)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder<ItemCommentBinding>, position: Int) {
        val binding = holder.binding
        val commentBean = list[position]
        binding.user.text = commentBean.userName
        binding.commentContent.text = commentBean.content
        binding.likeNum.text = "点赞数：${commentBean.likeNum}"
        binding.publishDate.text = "${commentBean.commentDate}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addVales(data: ArrayList<CommentBean>){
        data.forEach {
            list.add(it)
            notifyItemInserted(itemCount)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: ArrayList<CommentBean>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}