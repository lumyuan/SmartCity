package com.example.smartcity.activities

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartcity.R
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.bean.LoadState
import com.example.smartcity.databinding.ActivityNewsListBinding
import com.example.smartcity.model.NewsModel
import com.example.smartcity.ui.adapters.NewsListAdapter

class NewsListActivity : BaseActivity() {

    private val binding by binding(ActivityNewsListBinding::inflate)
    private lateinit var viewModel: NewsModel
    override fun initView() {
        super.initView()
        val extra = intent!!.getStringExtra("title")
        viewModel = ViewModelProvider(this)[NewsModel::class.java]
        setSupportActionBar(binding.toolBar)
        supportActionBar!!.title = getString(R.string.news)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.newsList.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.newsList.observe(this){
            binding.newsList.adapter = NewsListAdapter(it.rows!!)
        }

        //网络请求状态观察者
        viewModel.loadState.observe(this) {
            when (it) {
                is LoadState.Loading -> {
                    binding.none.apply {
                        visibility = View.VISIBLE
                        text = "全速加载资讯中......"
                    }

                }
                is LoadState.Success -> {
                    if (viewModel.newsList.value!!.rows!!.size > 0){
                        binding.none.visibility = View.GONE
                    }else {
                        binding.none.apply {
                            visibility = View.VISIBLE
                            text = "暂无资讯"
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

        //加载数据
        viewModel.getData(extra.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home-> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
}
    }

}