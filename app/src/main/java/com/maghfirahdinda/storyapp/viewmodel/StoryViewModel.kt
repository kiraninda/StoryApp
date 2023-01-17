package com.maghfirahdinda.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.maghfirahdinda.storyapp.model.StoryModel
import com.maghfirahdinda.storyapp.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val storyRepository: StoryRepository?) : ViewModel() {

    fun getAllStories():LiveData<List<StoryModel>> = storyRepository!!.getStories()

    fun getAllStoriesWithPage(
        token: String,
        location:Int
    ): LiveData<PagingData<StoryModel>> {
        return storyRepository!!.getStoriesWithPage(token,location).cachedIn(viewModelScope)
    }

}