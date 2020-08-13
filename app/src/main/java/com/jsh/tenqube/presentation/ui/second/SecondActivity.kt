package com.jsh.tenqube.presentation.ui.second

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jsh.tenqube.R
import com.jsh.tenqube.databinding.ActivitySubBinding
import com.jsh.tenqube.presentation.ui.first.FirstActivity
import com.jsh.tenqube.presentation.util.toLoadUrl
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
            setShopInfo(intent.getStringArrayListExtra("shopInfo")?: arrayListOf("","","",""))
        }
    }

    private fun observeData() {
        viewModel.deleteButtonClicked.observe(this, Observer {
            Toast.makeText(this, "Delete Complete", Toast.LENGTH_LONG).show()
            backToMain()
        })

        viewModel.editButtonClicked.observe(this, Observer {
            Toast.makeText(this, "Edit Complete", Toast.LENGTH_LONG).show()
            backToMain()
        })

        viewModel.addButtonClicked.observe(this, Observer {
            Toast.makeText(this, "Add Complete", Toast.LENGTH_LONG).show()
            backToMain()
        })

        viewModel.addImageButtonClicked.observe(this, Observer {
            viewModel.setImageUrl(resources.getString(R.string.testImage))
        })
    }

    private fun backToMain(){
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        finish()
    }
}