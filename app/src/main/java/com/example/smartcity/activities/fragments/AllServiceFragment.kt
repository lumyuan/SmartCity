package com.example.smartcity.activities.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.bean.LoadState
import com.example.smartcity.databinding.FragmentAllServiceBinding
import com.example.smartcity.model.AllServiceModel
import com.example.smartcity.net.pojo.ServiceBean
import com.example.smartcity.ui.adapters.AllServiceAdapter
import com.example.smartcity.ui.dialogs.SearchServiceDialog

class AllServiceFragment : BaseFragment() {

    private val binding by binding(FragmentAllServiceBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllServiceFragment()
    }

    private lateinit var serviceModel: AllServiceModel
    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)
        serviceModel = ViewModelProvider(this)[AllServiceModel::class.java]
        binding.serviceList.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        }

        //渲染服务列表
        serviceModel.serviceData.observe(this) {
            it.rows?.let { list->
                loadService(list)
            }
        }

        //网络请求状态观察者
        serviceModel.loadState.observe(this) {
            when (it) {
                is LoadState.Loading -> {
                    binding.none.apply {
                        visibility = View.VISIBLE
                        text = "服务加载中......"
                    }

                }
                is LoadState.Success -> {
                    if (serviceModel.serviceData.value!!.rows!!.size > 0){
                        binding.none.visibility = View.GONE
                    }else {
                        binding.none.apply {
                            visibility = View.VISIBLE
                            text = "暂无服务"
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

        //搜索服务
        binding.searchService.setOnClickListener {
            val list = serviceModel.searchService(binding.searchEditor.text.toString())
            SearchServiceDialog(list, activity).show()
        }

        serviceModel.getServiceData("N")
    }

    override fun loadData(activity: AppCompatActivity) {
        super.loadData(activity)
        //界面重载时关闭软键盘
        binding.searchEditor.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            requestFocus()
            setOnClickListener {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
                //唤起键盘
                handler.postDelayed({
                    try {
                        val inputMethodManager =
                            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.showSoftInput(this, 0)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }, 200)
            }
        }
    }

    private fun loadService(list: ArrayList<ServiceBean>){
        binding.serviceList.adapter = AllServiceAdapter(list)
    }
}