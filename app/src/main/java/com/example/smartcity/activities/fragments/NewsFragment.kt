package com.example.smartcity.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcity.R
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.databinding.FragmentNewsBinding

class NewsFragment : BaseFragment() {

    private val binding by binding(FragmentNewsBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)

    }
}