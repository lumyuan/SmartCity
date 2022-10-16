package com.example.smartcity.activities

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.bean.LoadState
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.databinding.ActivityFeedbackBinding
import com.example.smartcity.model.FeedbackModel
import com.example.smartcity.net.params.FeedbackBean
import com.example.smartcity.utils.ToastUtil
import com.gyf.immersionbar.ImmersionBar

class FeedbackActivity : BaseActivity() {
    private val binding by binding(ActivityFeedbackBinding::inflate)
    private lateinit var feedbackModel: FeedbackModel

    override fun initView() {
        super.initView()
        setSupportActionBar(binding.toolBar)
        supportActionBar!!.title = "意见反馈"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        feedbackModel = ViewModelProvider(this)[FeedbackModel::class.java]

        feedbackModel.feedbackData.observe(this) {
            ToastUtil.toast(it.msg!!)
        }

        ImmersionBar.with(this)
            .keyboardEnable(true)
            .init()

        //网络请求状态观察者
        feedbackModel.loadState.observe(this) {
            when (it) {
                is LoadState.Loading -> {
                }
                is LoadState.Success -> {
                    this@FeedbackActivity.finish()
                }
                is LoadState.Fail -> {
                }
            }
        }

        binding.postButton.setOnClickListener {
            val token = TokenLoader.load()
            if (token != null) {
                val title = binding.fbTitle.text.toString()
                val content = binding.fbContent.text.toString()
                if (content.length > 150) {
                    ToastUtil.toast("反馈内容长度最长为150字符")
                } else {
                    feedbackModel.postFeedback(
                        token, FeedbackBean().apply {
                            this.title = title
                            this.content = content
                        }
                    )
                }
            } else {
                this.finish()
                ToastUtil.toast("登录信息已过期，请重新登录")
            }
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
}