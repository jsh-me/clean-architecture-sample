package com.jsh.practice.presentation.ui.first

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsh.practice.R
import com.jsh.practice.databinding.ActivityMainBinding
import com.jsh.practice.presentation.entity.PresenterShop
import com.jsh.practice.presentation.ui.second.SecondActivity
import com.jsh.practice.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.Serializable

@AndroidEntryPoint
class FirstActivity : AppCompatActivity() {

    private val viewModel by viewModels<FirstViewModel>()
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var listAdapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Timber.e("Activity Created")

        setupBinding()
        observeData()
        setupListAdapter()
    }

    private fun setupBinding(){
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeData(){
        viewModel.openShopDetail.observe(this, Observer {
            openShopDetails(it)
        })

        viewModel.addShop.observe(this, Observer {
            val intent = Intent(this@FirstActivity, SecondActivity::class.java)
            intent.putExtra(EXTRA_USER_STATE, false)
            startActivity(intent)
        })
    }

    private fun openShopDetails(shopInfo: PresenterShop) {
        val intent = Intent(this@FirstActivity, SecondActivity::class.java)
        intent.putExtra(EXTRA_USER_STATE, true)
        intent.putExtra(EXTRA_SHOP_DETAIL, shopInfo as Serializable)
        startActivity(intent)
    }

    private fun setupListAdapter() {
        val viewModel = binding.viewmodel
        viewModel?.let{
            listAdapter = MainAdapter(viewModel)
            binding.mainRecycler.layoutManager = LinearLayoutManager(this)
            binding.mainRecycler.adapter = listAdapter
        }?: run {
            Timber.e("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.allLoad -> { return reLoadShopList() }
            R.id.allDelete -> { return clearShopList() }
            else -> { return super.onOptionsItemSelected(item) }
        }
    }

    private fun reLoadShopList(): Boolean {
        viewModel.reLoadButtonClick()
        return true
    }

    private fun clearShopList(): Boolean {
        viewModel.allDeleteButtonClick()
        binding.mainRecycler.adapter?.notifyDataSetChanged()
        return true
    }

    companion object {
        const val EXTRA_USER_STATE = "userState"
        const val EXTRA_SHOP_DETAIL = "shopDetail"
    }
}