package com.maghfirahdinda.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.maghfirahdinda.storyapp.Utils.Companion.BEARER
import com.maghfirahdinda.storyapp.database.StoryDatabase
import com.maghfirahdinda.storyapp.model.StoryModel
import com.maghfirahdinda.storyapp.network.ApiService
import com.maghfirahdinda.storyapp.viewmodel.CallBackResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService? = null
) {


    fun getStoriesWithPage(
        token: String,
        location: Int

    ): LiveData<PagingData<StoryModel>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),

            remoteMediator = StoryRemoteMediator(token, location, storyDatabase, apiService!!),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStoryWithPage()
            }
        ).liveData
    }

    fun getStoriesWithPage(

        location: Int

    ): LiveData<PagingData<StoryModel>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),

            remoteMediator = StoryRemoteMediator( location, storyDatabase, apiService!!),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStoryWithPage()
            }
        ).liveData
    }

     fun getStories(): LiveData<List<StoryModel>> = storyDatabase.storyDao().getAllStory()

     fun getStoriesWidget(): List<StoryModel> = storyDatabase.storyDao().getAllStoryWidget()



}