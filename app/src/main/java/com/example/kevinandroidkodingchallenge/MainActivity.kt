package com.example.kevinandroidkodingchallenge

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.network.Result
import com.example.core.data.response.NewsResponseItem
import com.example.kevinandroidkodingchallenge.data.adapter.NewsListAdapter
import com.example.kevinandroidkodingchallenge.databinding.ActivityMainBinding
import com.example.kevinandroidkodingchallenge.features.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()
    private val adapter: NewsListAdapter by lazy { NewsListAdapter() }
    private var newsData: List<NewsResponseItem> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.root.applySystemWindowInsets()

        val layoutManager = LinearLayoutManager(this)
        binding.rvNewsList.layoutManager = layoutManager
        binding.rvNewsList.adapter = adapter

        updateData()

        binding.ivSideMenu.setOnClickListener {
            PopupMenu(this, it).apply {
                menuInflater.inflate(R.menu.popup_menu, menu)
                setOnMenuItemClickListener{ item ->
                    when(item.itemId){
                        R.id.sortrd_by_rank -> {
                            adapter.submitListRankSorted(newsData)
                            true
                        }
                        R.id.sortrd_by_latest -> {
                            adapter.submitList(newsData)
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
                show()
            }
        }
    }
    private fun View.applySystemWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateData(){
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.getNews().observe(this@MainActivity) { result ->
                when (result) {
                    is Result.Success -> {
                        adapter.submitList(result.data)
                        newsData = result.data
                    }
                    is Result.Error -> {
                        Toast.makeText(this@MainActivity, "Error: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("MainViewModel", "Loading...")
                    }
                }
            }
        }
    }
}