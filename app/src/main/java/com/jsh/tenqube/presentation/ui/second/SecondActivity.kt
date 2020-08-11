package com.jsh.tenqube.presentation.ui.second

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jsh.tenqube.databinding.ActivitySubBinding
import com.jsh.tenqube.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private val viewModel by viewModels<SecondViewModel>()
    private val binding by viewBinding(ActivitySubBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.subViewModel = viewModel
        binding.lifecycleOwner = this

        initView()
        observeData()
    }

    private fun initView(){
        with(viewModel){
            setShopName(intent.getStringExtra("ShopName")?:"")
            setShopId(intent.getStringExtra("ShopId")?:"")
            setShopLabels(intent.getStringArrayListExtra(("ShopLabels"))?: arrayListOf())
            setShopUrl(intent.getStringExtra("ShopUrl")?:"")
        }
    }

    private fun observeData() {
        viewModel.deleteButtonClicked.observe(this, Observer {
            Toast.makeText(this, "Delete Complete", Toast.LENGTH_LONG).show()
            setResult(1200)
            finish()
        })

        viewModel.editButtonClicked.observe(this, Observer {
            Toast.makeText(this, "Edit Complete", Toast.LENGTH_LONG).show()
            setResult(1000)
            finish()
        })

        viewModel.addImageButtonClicked.observe(this, Observer {

        })
    }
}