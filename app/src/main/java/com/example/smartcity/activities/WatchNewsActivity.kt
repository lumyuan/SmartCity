package com.example.smartcity.activities

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.databinding.ActivityWatchNewsBinding
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.model.WatchNewsModel
import com.example.smartcity.net.pojo.CommentBean
import com.example.smartcity.net.pojo.NewsBean
import com.example.smartcity.net.pojo.PostCommentBean
import com.example.smartcity.net.pojo.ResponseListBean
import com.example.smartcity.ui.adapters.CommentAdapter
import com.example.smartcity.ui.adapters.TopNewsAdapter
import com.example.smartcity.utils.ToastUtil
import com.gyf.immersionbar.ImmersionBar
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
class WatchNewsActivity : BaseActivity() {

    private val binding by binding(ActivityWatchNewsBinding::inflate)
    private lateinit var viewModel: WatchNewsModel
    private lateinit var adapter: CommentAdapter
    private val dataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private var id: Int? = null
    override fun initView() {
        super.initView()
        ImmersionBar.with(this)
            .statusBarColor(R.color.white)
            .init()
        viewModel = ViewModelProvider(this)[WatchNewsModel::class.java]
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        binding.commentList.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            this@WatchNewsActivity.adapter = CommentAdapter()
            this.adapter = this@WatchNewsActivity.adapter
        }

        binding.recommendList.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }

        id = intent.getIntExtra("id", 0)

        viewModel.newsData.observe(this) {
            it?.let { newsBean->
                val data = newsBean.data!!
                supportActionBar!!.title = data.title
                supportActionBar!!.subtitle = data.subTitle
                Glide.with(binding.pick)
                    .load("${SmartCitySettingsModel.getWorkUrl()}${data.cover}")
                    .into(binding.pick)
                binding.content.text = Html.fromHtml(data.content)
                binding.readNum.text = "${data.readNum}"
                binding.commentNum.text = "${
                    if (data.commentNum == null){
                        "0"
                    }else {
                        data.commentNum
                    }
                }"
                binding.likeNum.text = "${data.likeNum}"
            }
        }

        viewModel.commentData.observe(this) {
            it?.let { data->
                val list = data.rows!!
                //按发布时间排序
                list.sortWith { o1, o2 ->
                    val d1 = dataFormat.parse(o1.commentDate!!)!!
                    val d2 = dataFormat.parse(o2.commentDate!!)!!
                    if (d1 > d2) {
                        -1
                    } else {
                        1
                    }
                }
                adapter.updateData(list)
            }
        }

        viewModel.topNewsData.observe(this) {
            it?.let {
                showTopNews(it)
            }
        }

        //网络请求状态观察者
        viewModel.loadState.observe(this) {
            showCommentLoader(it)
        }


        binding.postButton.setOnClickListener {
            postComment()
        }

        try {
            viewModel.watchNews(id.toString().toInt())
            viewModel.watchComment(id.toString().toInt())
            viewModel.getTopNews()
        }catch (e: Exception){
            e.printStackTrace()
            this.finish()
        }
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

    private fun showCommentLoader(it: LoadState){
        if (it.message != "comment"){
            return
        }
        when (it) {
            is LoadState.Loading -> {
                binding.none.apply {
                    visibility = View.VISIBLE
                    text = "加载评论中......"
                }

            }
            is LoadState.Success -> {
                if (viewModel.commentData.value?.rows?.size!! > 0){
                    binding.none.visibility = View.GONE
                }else {
                    binding.none.apply {
                        visibility = View.VISIBLE
                        text = "暂无评论"
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

    /**
     * 发布评论状态监听
     */
    private fun postCommentState(it: LoadState){
        if (it.message != "postComment"){
            return
        }
        when (it) {
            is LoadState.Loading -> {

            }
            is LoadState.Success -> {
                binding.write.setText("")
                binding.appBar.isLifted = true
                viewModel.watchComment(id!!)
                ToastUtil.toast("评论成功")
            }
            is LoadState.Fail -> {
                ToastUtil.toast("评论成功")
                binding.appBar.isLifted = true
            }
        }
    }

    /**
     * 显示推荐新闻
     */
    private fun showTopNews(it: ResponseListBean<NewsBean>){
        val rows = it.rows!!
        binding.recommendList.adapter = TopNewsAdapter(rows)
    }

    /**
     * 发布评论
     */
    private fun postComment(){
        val token = TokenLoader.load()
        if (token != null){
            viewModel.postComment(

                token, PostCommentBean().apply {
                    this.content = binding.write.text.toString()
                    this.newsId = id!!
                }
            )
        }else {
            ToastUtil.toast("登录信息已过期，请重新登录")
            this.finish()
        }
    }
}