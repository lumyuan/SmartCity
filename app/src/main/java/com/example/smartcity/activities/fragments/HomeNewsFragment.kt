package com.example.smartcity.activities.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.bean.LoadState
import com.example.smartcity.databinding.FragmentHomeNewsBinding
import com.example.smartcity.model.HomeNewsModel
import com.example.smartcity.net.pojo.NewsBean
import com.example.smartcity.ui.adapters.NewsListAdapter
import java.text.SimpleDateFormat

private const val ARG_TITLE = "title"

class HomeNewsFragment : BaseFragment() {
    private var title: String? = null
    private val binding by binding(FragmentHomeNewsBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return binding.root
    }

    companion object {
        @JvmStatic fun newInstance(title: String) =
            HomeNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
    }

    private lateinit var viewModel: HomeNewsModel
    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)
        viewModel = ViewModelProvider(this)[HomeNewsModel::class.java]
        binding.list.apply {
            this.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }

        //加载新闻资讯
        viewModel.newsData.observe(this){
            val list = it.rows!!
            //按发布时间排序
            list.sortWith { o1, o2 ->
                val od = dateFormat.parse(o1?.publishDate!!)
                val nd = dateFormat.parse(o2?.publishDate!!)
                if (od!! > nd) {
                    -1
                } else {
                    1
                }
            }
            binding.list.adapter = NewsListAdapter(list)
        }

        //网络请求状态
        viewModel.loadState.observe(this) {
            when (it) {
                is LoadState.Loading -> {
                    binding.none.apply {
                        visibility = View.VISIBLE
                        text = "全速加载资讯中......"
                    }

                }
                is LoadState.Success -> {
                    if (viewModel.newsData.value!!.rows!!.size > 0){
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
        viewModel.getNews(title!!)
    }
}