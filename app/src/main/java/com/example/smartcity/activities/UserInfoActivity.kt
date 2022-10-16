package com.example.smartcity.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toFile
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.smartcity.R
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.core.UserLoginLoader
import com.example.smartcity.databinding.ActivityUserInfoBinding
import com.example.smartcity.model.GetUserModel
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.net.params.UpdateUserBean
import com.example.smartcity.ui.dialogs.UpdateUserDialog
import com.example.smartcity.utils.PrivateFileUtils
import com.example.smartcity.utils.ToastUtil
import java.io.File

class UserInfoActivity : BaseActivity() {

    private val binding by binding(ActivityUserInfoBinding::inflate)

    companion object {
        const val ACTION_SELECT_PICK = 100
        const val ACTION_CROP_PICK = 110
    }

    private lateinit var getUserModel: GetUserModel
    private var token: String? = null
    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()

        getUserModel = ViewModelProvider(this)[GetUserModel::class.java]
        token = TokenLoader.load()
        println(token)

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

        //选择图片
        binding.userIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, ACTION_SELECT_PICK)
        }

        //注册获取用户信息观察者
        getUserModel.userData.observe(this) {
            println(it)
            if (it != null && it.code == 200) {
                val user = it.user
                Glide.with(binding.userIcon)
                    .load("${SmartCitySettingsModel.getWorkUrl()}${user!!.avatar}")
                    .error(R.mipmap.ic_launcher)
                    .into(binding.userIcon)
                binding.nickname.text = if (user.nickName != null) {
                    user.nickName
                } else {
                    "User-${user.userId}"
                }
                binding.username.text = "账户：${user.userName}"
                binding.idCard.text = if (user.idCard != null && user.idCard != "") {
                    "身份证：${user.idCard}"
                } else {
                    "身份证：未绑定"
                }
                binding.sex.setText(
                    if (user.sex == "1") {
                        R.string.woman
                    } else {
                        R.string.man
                    }
                )
                binding.email.text = if (user.email != null && user.email != "") {
                    user.email
                } else {
                    "未绑定"
                }
                binding.tel.text = user.phonenumber
                binding.balance.text = user.balance.toString()
            }
        }

        //获取本地token
        if (token != null) {
            //获取用户信息网络请求
            getUserModel.getUser(
                token!!
            )
        } else {
            ToastUtil.toast("登录信息已过期，请重新登录")
            this.finish()
        }

        //注册文件上传状态观察者
        getUserModel.uploadFileResponseData.observe(this) {
            if (it != null && it.code == 200){
                if (token != null) {
                    //获取用户信息网络请求
                    getUserModel.updateUser(
                        token!!, UpdateUserBean().apply {
                            this.nickName = binding.nickname.text.toString()
                            //修改头像
                            this.avatar = "/prod-api${it.fileName}"
                        }
                    )
                    ToastUtil.toast("头像上传成功")
                } else {
                    ToastUtil.toast("登录信息已过期，请重新登录")
                    ToastUtil.toast("登录信息已过期，请重新登录")
                    this.finish()
                }
            }
        }

        //用户更新观察者
        getUserModel.updateUserData.observe(this) {
            if (it != null && it.code == 200){
                //获取本地token
                if (token != null) {
                    //获取用户信息网络请求
                    getUserModel.getUser(
                        token!!
                    )
                    ToastUtil.toast("用户信息修改成功")
                } else {
                    ToastUtil.toast("登录信息已过期，请重新登录")
                    this.finish()
                }
            }
        }

        binding.nickname.setOnClickListener {
            showDialog(UpdateUserDialog.TYPE_NICKNAME)
        }

        binding.idCard.setOnClickListener {
            showDialog(UpdateUserDialog.TYPE_ID_CARD)
        }

        binding.updateEmail.setOnClickListener {
            showDialog(UpdateUserDialog.TYPE_EMAIL)
        }

        binding.updateTel.setOnClickListener {
            showDialog(UpdateUserDialog.TYPE_TEL)
        }

        binding.updateSex.setOnClickListener {
            showDialog(UpdateUserDialog.TYPE_SEX)
        }
    }

    private fun showDialog(type: String) {
        UpdateUserDialog(this, binding.nickname.text.toString(), type).show()
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

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, data)",
            "com.example.smartcity.base.BaseActivity"
        )
    )
    @SuppressLint("StaticFieldLeak")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        if (resultCode == Activity.RESULT_OK && uri != null){
            when (requestCode) {
                ACTION_SELECT_PICK -> {
                    println(uri)
                    val documentFile = DocumentFile.fromSingleUri(this, uri)
                    cropPick(documentFile!!)
                }
                ACTION_CROP_PICK -> {
                    val token = TokenLoader.load()
                    if (token != null) {
                        //获取用户信息网络请求
                        val cropFile = getCropFile(uri)
                        getUserModel.uploadFile(cropFile, token)
                    } else {
                        ToastUtil.toast("登录信息已过期，请重新登录")
                        this.finish()
                    }

                }
            }
        }
    }

    //调用系统裁剪
    private fun cropPick(documentFile: DocumentFile){
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(documentFile.uri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, PrivateFileUtils.cropPick(documentFile.name!!))
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG)
        intent.putExtra("outputX", 380)
        intent.putExtra("outputY", 380)
        intent.putExtra("scale", true)
        intent.putExtra("scaleUpIfNeeded", true)
        intent.putExtra("return-data", false)
        startActivityForResult(intent, ACTION_CROP_PICK)
    }

    //Uri处理
    private fun getCropFile(uri: Uri): File {
        return if (uri.toString().startsWith("file:///")) {
            uri.toFile()
        } else {
            val documentFile = DocumentFile.fromSingleUri(this, uri)
            PrivateFileUtils.copy(
                this.contentResolver.openInputStream(documentFile!!.uri)!!,
                documentFile.name!!
            )
        }
    }
}