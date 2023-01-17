package com.maghfirahdinda.storyapp.viewmodel

interface CallBackResponse {
    fun showLoading()
    fun dismissLoading()
    fun onSuccess(message: String)
    fun onFailure(message: String)

}