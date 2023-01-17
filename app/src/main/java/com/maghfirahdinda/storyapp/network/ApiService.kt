package com.maghfirahdinda.storyapp.network

import com.maghfirahdinda.storyapp.model.StoryResponse
import retrofit2.http.*

interface ApiService {
    @GET("stories")
    suspend fun getStoriesWithPage(@Header("Authorization") token: String, @Query("page")page:Int,@Query("size")size:Int, @Query("location") location:Int): StoryResponse
}