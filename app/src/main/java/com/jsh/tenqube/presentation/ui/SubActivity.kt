package com.jsh.tenqube.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jsh.tenqube.R
import com.jsh.tenqube.databinding.ActivityMainBinding
import com.jsh.tenqube.databinding.ActivitySubBinding
import com.jsh.tenqube.presentation.util.toLoadUrl
import com.jsh.tenqube.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SubActivity : AppCompatActivity() {

    private val viewModel by viewModels<SubViewModel>()
    private val binding by viewBinding(ActivitySubBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.subViewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        initView()
        observeData()
    }

    private fun initView(){
        binding.shopNameEdit.setText(intent.getStringExtra("ShopName"))
        binding.detailShopImage.toLoadUrl(intent.getStringExtra("ShopUrl")?:"")
    }

    private fun observeData() {
        viewModel.shopName.observe(this, Observer {
            binding.shopNameEdit.setText(it)
        })
    }
}