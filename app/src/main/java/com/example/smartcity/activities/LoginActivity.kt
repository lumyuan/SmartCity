package com.example.smartcity.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity.R
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.bean.LoadState
import com.example.smartcity.bean.UserLoginBean
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.core.UserLoginLoader
import com.example.smartcity.databinding.ActivityLoginBinding
import com.example.smartcity.model.LoginModel
import com.example.smartcity.net.Repository
import com.example.smartcity.net.params.LoginBean
import com.example.smartcity.net.params.RegisterBean
import com.example.smartcity.utils.LogUtils
import com.example.smartcity.utils.ToastUtil
import com.gyf.immersionbar.ImmersionBar

class LoginActivity : BaseActivity() {

    private val binding by binding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .keyboardEnable(true)
            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            .init()
    }

    private lateinit var viewModel: LoginModel
    override fun initView() {
        super.initView()

        viewModel = ViewModelProvider(this)[LoginModel::class.java]

        binding.man.isChecked = true
        //切换登录或注册
        binding.loginInOrUp.setOnClickListener {
            //清空文本框内容
            cleanContent()
            when (binding.title.text.toString()) {
                getString(R.string.login_in) -> {
                    toSignUp()
                }
                else -> {
                    toSignIn()
                }
            }
        }

        //提交按钮点击事件：注册或登录
        binding.postButton.setOnClickListener {
            if (viewModel.loadState.value is LoadState.Loading) {
                ToastUtil.toast("请求数据中，请稍候...")
            } else {
                when (binding.title.text.toString()) {
                    getString(R.string.login_in) -> {
                        login()
                    }
                    else -> {
                        register()
                    }
                }
            }
        }

        //注册登录状态观察者
        viewModel.token.observe(this) {
            if (it.code == 200 && it.token != null) {
                //登录成功  保存用户信息----加密
                UserLoginLoader.save(UserLoginBean().apply {
                    this.userName = binding.userName.text.toString()
                    this.password = binding.password.text.toString()
                })
                //保存Token
                TokenLoader.save(it.token!!)
                this.finish()
            }
        }

        //注册注册监听状态观察者
        viewModel.register.observe(this) {
            if (it.code == 200){
                toSignIn()
            }
        }

        //请求状态监听
        viewModel.loadState.observe(this) {
            when (it) {
                is LoadState.Success -> {
                    ToastUtil.toast(
                        when (binding.title.text.toString()) {
                            getString(R.string.login_in) -> {
                                viewModel.token.value!!.msg!!
                            }
                            else -> {
                                viewModel.register.value!!.msg!!
                            }
                        }
                    )
                }
                else -> {}
            }
        }
    }

    private fun cleanContent() {
        binding.userName.setText("")
        binding.password.setText("")
    }

    private fun toSignUp(){
        binding.title.setText(R.string.login_up)
        binding.cPasswordLayout.visibility = View.VISIBLE
        binding.telLayout.visibility = View.VISIBLE
        binding.sexLayout.visibility = View.VISIBLE
        binding.loginInOrUp.setText(R.string.to_login_in)
        binding.postButton.setText(R.string.login_up)
    }

    private fun toSignIn(){
        binding.title.setText(R.string.login_in)
        binding.cPasswordLayout.visibility = View.GONE
        binding.telLayout.visibility = View.GONE
        binding.sexLayout.visibility = View.GONE
        binding.loginInOrUp.setText(R.string.to_login_up)
        binding.postButton.setText(R.string.login_in)
    }

    /**
     * 登录
     */
    private fun login() {
        val userName = binding.userName.text.toString().replace(" ", "")
        val password = binding.password.text.toString().replace(" ", "")
        if (userName != "" && userName != "null" && password != "" && password != "null") {
            viewModel.login(
                LoginBean().apply {
                    this.username = userName
                    this.password = password
                }
            )
        } else {
            ToastUtil.toast("用户名或密码不完整")
        }
    }

    /**
     * 注册
     */
    private fun register() {
        val userName = binding.userName.text.toString().replace(" ", "")
        val password = binding.password.text.toString().replace(" ", "")
        val cPassword = binding.cPassword.text.toString().replace(" ", "")
        val tel = binding.tel.text.toString()
        val sex = if (binding.man.isChecked) {
            "0"
        } else {
            "1"
        }
        if (userName == "" || userName == "null" || password == "" || password == "null" || cPassword == "" || cPassword == "null") {
            ToastUtil.toast("内容填写不完整")
        } else if (password != cPassword) {
            ToastUtil.toast("两次密码填写不一致")
        } else {
            viewModel.register(
                RegisterBean().apply {
                    this.userName = userName
                    this.password = password
                    this.phonenumber = tel
                    this.sex = sex
                }
            )
        }
    }
}