package com.jsh.practice.presentation.ui.second

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jsh.practice.R
import com.jsh.practice.databinding.ActivitySubBinding
import com.jsh.practice.presentation.entity.PresenterShop
import com.jsh.practice.presentation.ui.first.FirstActivity
import com.jsh.practice.presentation.ui.first.FirstActivity.Companion.EXTRA_SHOP_DETAIL
import com.jsh.practice.presentation.ui.first.FirstActivity.Companion.EXTRA_USER_STATE
import com.jsh.practice.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private val viewModel by viewModels<SecondViewModel>()
    private val binding by viewBinding(ActivitySubBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBinding()
        initView()
        observeData()
    }

    private fun setupBinding() {
        binding.subViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initView() {
        if (intent.getBooleanExtra(EXTRA_USER_STATE, false)) {
            with(viewModel) {
                setShopInfo(intent.getSerializableExtra(EXTRA_SHOP_DETAIL) as PresenterShop)
            }
        }
    }

    private fun observeData() {
        viewModel.deleteShop.observe(this, Observer {
            if(it == true) {
                Toast.makeText(this, "Delete Complete", Toast.LENGTH_SHORT).show()
                backToMain()
            } else {
                Toast.makeText(this, "Delete Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.editShop.observe(this, Observer {
            if(it == true){
                Toast.makeText(this, "Edit Complete", Toast.LENGTH_SHORT).show()
                backToMain()
            } else {
                Toast.makeText(this, "Edit Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.addShop.observe(this, Observer {
            if(it == true) {
                Toast.makeText(this, "Add Complete", Toast.LENGTH_SHORT).show()
                backToMain()
            } else {
                Toast.makeText(this, "Add Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.addShopImage.observe(this, Observer {
            viewModel.setImageUrl(resources.getString(R.string.testImage))
        })
    }

    private fun backToMain() {
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        finish()
    }
}