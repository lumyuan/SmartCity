package com.example.smartcity.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity.R
import com.example.smartcity.bean.LoadState
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.core.UserLoginLoader
import com.example.smartcity.databinding.DialogUpdatePasswordBinding
import com.example.smartcity.model.GetUserModel
import com.example.smartcity.model.UpdatePasswordModel
import com.example.smartcity.net.params.UpdatePasswordBean
import com.example.smartcity.utils.ToastUtil

class UpdatePasswordDialog(private val activity: AppCompatActivity): Dialog(activity, R.style.DialogTheme) {

    private lateinit var binding: DialogUpdatePasswordBinding
    private lateinit var viewModel: UpdatePasswordModel
    private lateinit var egtUSerModel: GetUserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogUpdatePasswordBinding.bind(
            View.inflate(activity, R.layout.dialog_update_password, null)
        )
        viewModel = ViewModelProvider(activity)[UpdatePasswordModel::class.java]
        egtUSerModel = ViewModelProvider(activity)[GetUserModel::class.java]

        setContentView(binding.root)

        val layoutParams = window?.attributes
        val params = layoutParams?.apply {
            this.width = WindowManager.LayoutParams.MATCH_PARENT
            this.height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
        }
        window?.attributes = params

        //网络请求状态观察者
        viewModel.loadState.observe(activity) {
            when (it) {
                is LoadState.Loading -> {
                }
                is LoadState.Success -> {
                    UserLoginLoader.remove()
                    TokenLoader.remove()
                    dismiss()
                    egtUSerModel.userData.value = null
                }
                is LoadState.Fail -> {
                }
            }
        }

        viewModel.updatePasswordData.observe(activity){
            try {
                ToastUtil.toast(
                    if(it.code == 200){
                        "密码修改成功，请重新登录"
                    }else {
                        it.msg!!
                    }
                )
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.postButton.setOnClickListener {
            val old = binding.oldPassword.text.toString()
            val new = binding.newPassword.text.toString()
            val token = TokenLoader.load()
            if (token != null){
                viewModel.updatePassword(
                    token, UpdatePasswordBean().apply {
                        this.oldPassword = old
                        this.newPassword = new
                    }
                )
            }else {
                ToastUtil.toast("登录信息已过期，请重新登录")
                dismiss()
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        viewModel.updatePasswordData.value = null
        viewModel.loadState.value = null
        viewModel.updatePasswordData.removeObservers(activity)
        viewModel.loadState.removeObservers(activity)
    }
}