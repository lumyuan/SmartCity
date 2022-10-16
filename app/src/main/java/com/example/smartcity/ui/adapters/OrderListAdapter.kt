package com.example.smartcity.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcity.R
import com.example.smartcity.base.MyViewHolder
import com.example.smartcity.databinding.ItemOrderBinding
import com.example.smartcity.net.pojo.OrderBean

class OrderListAdapter(private val list: ArrayList<OrderBean>): RecyclerView.Adapter<MyViewHolder<ItemOrderBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<ItemOrderBinding> {
        return MyViewHolder(
            ItemOrderBinding.bind(
                View.inflate(parent.context, R.layout.item_order, null)
            )
        )
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: MyViewHolder<ItemOrderBinding>, position: Int) {
        val binding = holder.binding
        val orderBean = list[position]
        binding.sellerName.text = orderBean.name
        binding.orderStatus.text = orderBean.orderStatus
        binding.orderNo.text = orderBean.orderNo
        binding.orderTypeName.text = orderBean.orderTypeName
        binding.payTime.text = orderBean.payTime
        binding.amount.text = "￥${orderBean.amount}"
        if (orderBean.orderStatus == "未付款"){
            binding.orderStatus.setTextColor(Color.parseColor(binding.root.context.getString(R.color.red)))
        }else {
            binding.orderStatus.setTextColor(Color.parseColor(binding.root.context.getString(R.color.gray_text)))
        }
        if (itemCount - 1 == position){
            binding.toBottom.visibility = View.VISIBLE
        }else{
            binding.toBottom.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}