package com.example.smartcity.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.activities.LoginActivity
import com.example.smartcity.activities.UserInfoActivity
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.core.UserLoginLoader
import com.example.smartcity.databinding.FragmentMineStoreBinding
import com.example.smartcity.model.GetUserModel
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.utils.FastFunctionUtils

class MineStoreFragment : BaseFragment() {

    private val binding by binding(FragmentMineStoreBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MineStoreFragment()
    }

    private lateinit var getUserModel: GetUserModel
    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)
        getUserModel = ViewModelProvider(activity)[GetUserModel::class.java]
        binding.gotoLogin.setOnClickListener {
            FastFunctionUtils.startActivity(activity, LoginActivity::class.java)
        }

        //显示用户信息
        getUserModel.userData.observe(this){
            if (it != null){
                val user = it.user
                Glide.with(binding.userIcon)
                    .load("${SmartCitySettingsModel.getWorkUrl()}${user!!.avatar}")
                    .error(R.mipmap.ic_launcher)
                    .into(binding.userIcon)
                binding.userName.text = user.userName
            }
            showUser(it != null)
        }

        //退出登录
        binding.signOut.setOnClickListener {
            AlertDialog.Builder(activity).apply {
                setIcon(R.mipmap.ic_launcher)
                setTitle("提示")
                setMessage("退出登录后无法享受更多权益，确认退出登录吗")
                setPositiveButton("退出登录"){_, _ ->
                    getUserModel.userData.value = null
                    UserLoginLoader.remove()
                    TokenLoader.remove()
                }
                setNegativeButton("取消", null)
            }.show()
        }

        //进入用户信息页
        binding.gotoUserInfo.setOnClickListener {
            FastFunctionUtils.startActivity(activity, UserInfoActivity::class.java)
        }
    }

    private fun showUser(isShow: Boolean){
        if (isShow) {
            binding.gotoLogin.visibility = View.GONE
            binding.userIcon.visibility = View.VISIBLE
            binding.userLayout.visibility = View.VISIBLE
        }else {
            binding.gotoLogin.visibility = View.VISIBLE
            binding.userIcon.visibility = View.GONE
            binding.userLayout.visibility = View.GONE
        }
    }
}