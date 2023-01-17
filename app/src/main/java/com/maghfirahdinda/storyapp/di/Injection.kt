package com.maghfirahdinda.storyapp.di

import android.content.Context
import com.maghfirahdinda.storyapp.database.StoryDatabase
import com.maghfirahdinda.storyapp.network.ApiConfig
import com.maghfirahdinda.storyapp.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}