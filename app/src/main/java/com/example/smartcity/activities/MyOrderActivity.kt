package com.example.smartcity.activities

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.bean.LoadState
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.databinding.ActivityMyOrderBinding
import com.example.smartcity.model.OrderModel
import com.example.smartcity.net.pojo.OrderBean
import com.example.smartcity.ui.adapters.OrderListAdapter
import com.example.smartcity.utils.ToastUtil

class MyOrderActivity : BaseActivity() {
    private val binding by binding(ActivityMyOrderBinding::inflate)
    private lateinit var viewModel: OrderModel
    override fun initView() {
        super.initView()
        viewModel = ViewModelProvider(this)[OrderModel::class.java]
        binding.orderList.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }
        setSupportActionBar(binding.toolBar)
        supportActionBar!!.title = "我的订单"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //订单数据观察者
        viewModel.orderData.observe(this) {
            if (it.code == 200) {
                loadData(it.rows!!)
            }
        }

        //网络请求状态观察者
        viewModel.loadState.observe(this) {
            when (it) {
                is LoadState.Loading -> {
                    binding.none.apply {
                        visibility = View.VISIBLE
                        text = "加载中......"
                    }

                }
                is LoadState.Success -> {
                    if (viewModel.orderData.value!!.rows!!.size > 0) {
                        binding.none.visibility = View.GONE
                    } else {
                        binding.none.apply {
                            visibility = View.VISIBLE
                            text = "暂无订单信息"
                        }
                    }
                }
                is LoadState.Fail -> {
                    binding.none.apply {
                        visibility = View.VISIBLE
                        text = "加载失败了......"
                    }
                }
            }
        }

        val token = TokenLoader.load()
        if (token != null) {
            //加载订单信息
            viewModel.getOrderList(token)
        } else {
            ToastUtil.toast("登录信息已过期，请重新登录")
            this.finish()
        }
    }

    private fun loadData(list: ArrayList<OrderBean>) {
        binding.orderList.adapter = OrderListAdapter(list)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}