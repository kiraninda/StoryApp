package com.maghfirahdinda.storyapp.model

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: MutableList<StoryModel>
)