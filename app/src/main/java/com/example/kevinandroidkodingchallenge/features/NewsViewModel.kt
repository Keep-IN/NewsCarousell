package com.example.kevinandroidkodingchallenge.features

import androidx.lifecycle.ViewModel
import com.example.core.data.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {
    fun getNews() = repository.getNews()
}