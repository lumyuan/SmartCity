package com.example.smartcity.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity.R
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.databinding.DialogUpdateUserBinding
import com.example.smartcity.model.GetUserModel
import com.example.smartcity.net.params.UpdateUserBean
import com.example.smartcity.net.pojo.GetUserBean
import com.example.smartcity.utils.ToastUtil

class UpdateUserDialog(private val context: BaseActivity, private val nickname: String, private val type: String): Dialog(context, R.style.DialogTheme) {

    companion object {
        const val TYPE_NICKNAME = "nickname"
        const val TYPE_ID_CARD = "idCard"
        const val TYPE_EMAIL = "email"
        const val TYPE_TEL = "tel"
        const val TYPE_SEX = "sex"
    }

    private lateinit var binding: DialogUpdateUserBinding
    private var token: String? = null
    private lateinit var viewModel: GetUserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogUpdateUserBinding.bind(
            View.inflate(context, R.layout.dialog_update_user, null)
        )
        token = TokenLoader.load()
        viewModel = ViewModelProvider(context)[GetUserModel::class.java]
        setContentView(binding.root)
        val layoutParams = window?.attributes
        val params = layoutParams?.apply {
            this.width = WindowManager.LayoutParams.MATCH_PARENT
            this.height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
        }
        window?.attributes = params
        showView(type)

        //获取用户信息观察者
        viewModel.userData.observe(context) {
            initView(it)
        }

        //修改用户信息观察者
        viewModel.updateUserData.observe(context){
            if (it.code == 200){
                //更新用户信息
                viewModel.getUser()
                dismiss()
            }
        }

        //修改用户信息
        binding.postButton.setOnClickListener {
            post(type)
        }
    }

    private fun initView(getUserBean: GetUserBean?){
        if (getUserBean != null && getUserBean.code == 200){
            val user = getUserBean.user
            binding.nickname.setText(user?.nickName)
            binding.idCard.setText(user?.idCard)
            binding.email.setText(user?.email)
            binding.tel.setText(user?.phonenumber)
            if (user?.sex == "1"){
                binding.woman.isChecked = true
            }else {
                binding.man.isChecked = true
            }
        }else {
            dismiss()
        }
    }

    private fun showView(type: String){
        when (type) {
            TYPE_NICKNAME -> {
                binding.nicknameLayout.visibility = View.VISIBLE
                binding.emailLayout.visibility = View.GONE
                binding.idCardLayout.visibility = View.GONE
                binding.telLayout.visibility = View.GONE
                binding.sexLayout.visibility = View.GONE
            }
            TYPE_EMAIL -> {
                binding.nicknameLayout.visibility = View.GONE
                binding.emailLayout.visibility = View.VISIBLE
                binding.idCardLayout.visibility = View.GONE
                binding.telLayout.visibility = View.GONE
                binding.sexLayout.visibility = View.GONE
            }
            TYPE_ID_CARD -> {
                binding.nicknameLayout.visibility = View.GONE
                binding.emailLayout.visibility = View.GONE
                binding.idCardLayout.visibility = View.VISIBLE
                binding.telLayout.visibility = View.GONE
                binding.sexLayout.visibility = View.GONE
            }
            TYPE_TEL -> {
                binding.nicknameLayout.visibility = View.GONE
                binding.emailLayout.visibility = View.GONE
                binding.idCardLayout.visibility = View.GONE
                binding.telLayout.visibility = View.VISIBLE
                binding.sexLayout.visibility = View.GONE
            }
            TYPE_SEX -> {
                binding.nicknameLayout.visibility = View.GONE
                binding.emailLayout.visibility = View.GONE
                binding.idCardLayout.visibility = View.GONE
                binding.telLayout.visibility = View.GONE
                binding.sexLayout.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 提交修改id用户信息
     */
    private fun post(type: String){
        if (token != null){
            when (type) {
                TYPE_NICKNAME -> {
                    viewModel.updateUser(
                        token!!, UpdateUserBean(binding.nickname.text.toString())
                    )
                }
                TYPE_EMAIL -> {
                    viewModel.updateUser(
                        token!!, UpdateUserBean(nickname).apply {
                            this.email = binding.email.text.toString()
                        }
                    )
                }
                TYPE_ID_CARD -> {
                    viewModel.updateUser(
                        token!!, UpdateUserBean(nickname).apply {
                            this.idCard = binding.idCard.text.toString()
                        }
                    )
                }
                TYPE_TEL -> {
                    viewModel.updateUser(
                        token!!, UpdateUserBean(nickname).apply {
                            this.phonenumber = binding.tel.text.toString()
                        }
                    )
                }
                TYPE_SEX -> {
                    viewModel.updateUser(
                        token!!, UpdateUserBean(nickname).apply {
                            this.sex = if (binding.woman.isChecked){
                                "1"
                            }else {
                                "0"
                            }
                        }
                    )
                }
            }
        }else {
            ToastUtil.toast("登录信息已过期，请重新登录")
        }
    }
}