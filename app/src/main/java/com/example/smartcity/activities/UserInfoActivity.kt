package com.example.smartcity.activities

import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.core.UserLoginLoader
import com.example.smartcity.databinding.ActivityUserInfoBinding
import com.example.smartcity.model.GetUserModel
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.utils.ToastUtil

class UserInfoActivity : BaseActivity() {

    private val binding by binding(ActivityUserInfoBinding::inflate)

    private lateinit var getUserModel: GetUserModel
    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        getUserModel = ViewModelProvider(this)[GetUserModel::class.java]

        setSupportActionBar(binding.toolBar)
        this.supportActionBar!!.title = "个人信息"
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //退出登录
        binding.signOut.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setIcon(R.mipmap.ic_launcher)
                setTitle("提示")
                setMessage("退出登录后无法享受更多权益，确认退出登录吗")
                setPositiveButton("退出登录") { _, _ ->
                    getUserModel.userData.value = null
                    UserLoginLoader.remove()
                    TokenLoader.remove()
                    this@UserInfoActivity.finish()
                }
                setNegativeButton("取消", null)
            }.show()
        }

        //注册获取用户信息观察者
        getUserModel.userData.observe(this) {
            if (it != null && it.code == 200){
                val user = it.user
                Glide.with(binding.userIcon)
                    .load("${SmartCitySettingsModel.getWorkUrl()}${user!!.avatar}")
                    .error(R.mipmap.ic_launcher)
                    .into(binding.userIcon)
                binding.nickname.text = if (user.nickName != null && user.nickName != ""){
                    user.userName
                }else {
                    "User-${user.userId}"
                }
                binding.username.text = "账户：${user.userName}"
                binding.idCard.text = if (user.idCard != null && user.idCard != ""){
                    "身份证：${user.idCard}"
                }else {
                    "身份证：未绑定"
                }
                binding.sex.setText(
                    if (user.sex == "1"){
                        R.string.woman
                    }else {
                        R.string.man
                    }
                )
                binding.email.text = if (user.email != null && user.email != ""){
                    user.email
                }else {
                    "未绑定"
                }
                binding.tel.text = user.phonenumber
                binding.balance.text = user.balance.toString()
            }
        }

        //获取本地token
        val token = TokenLoader.load()
        if (token != null){
            //获取用户信息网络请求
            getUserModel.getUser(
                token
            )
        }else {
            ToastUtil.toast("登录信息已过期，请重新登录")
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

}