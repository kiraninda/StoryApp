package com.maghfirahdinda.storyapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maghfirahdinda.storyapp.R
import com.maghfirahdinda.storyapp.databinding.ActivityMainBinding
import com.maghfirahdinda.storyapp.adapter.ListStoryAdapter
import com.maghfirahdinda.storyapp.adapter.LoadingStateAdapter
import com.maghfirahdinda.storyapp.di.Injection
import com.maghfirahdinda.storyapp.model.StoryModel
import com.maghfirahdinda.storyapp.preference.StoryPreference
import com.maghfirahdinda.storyapp.repository.StoryRepository
import com.maghfirahdinda.storyapp.viewmodel.CallBackResponse
import com.maghfirahdinda.storyapp.viewmodel.StoryViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity(), CallBackResponse {

    private lateinit var storyViewModel: StoryViewModel

    private lateinit var storyRepository: StoryRepository
    private lateinit var binding: ActivityMainBinding
    private  var listStory:List<StoryModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyRepository =Injection.provideRepository(this)
        storyViewModel = ViewModelProvider(
            this,
            ViewModelFactory(storyRepository = storyRepository)
        )[StoryViewModel::class.java]
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStories.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStories.layoutManager = LinearLayoutManager(this)
        }
        val listStoryAdapter = ListStoryAdapter()

        binding.rvStories.adapter = listStoryAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                listStoryAdapter.retry()
            }
        )
        storyViewModel.getAllStoriesWithPage(Utils.TOKEN,1).observe(this) { pagingData ->

            showRecyclerList(pagingData,listStoryAdapter)
        }


        storyViewModel.getAllStories().observe(this){
            listStory= it
        }


    }


    private fun showRecyclerList(listStory: PagingData<StoryModel>,listStoryAdapter:ListStoryAdapter) {
        listStoryAdapter.submitData(lifecycle,listStory)
    }

    override fun showLoading() {
        binding.layoutProgress.placeProgressBar.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        binding.layoutProgress.placeProgressBar.visibility = View.GONE
    }

    override fun onSuccess(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.success))
            setMessage(message)
            setPositiveButton(getString(R.string.next)) { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    override fun onFailure(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.failure))
            setMessage(message)
            setPositiveButton(getString(R.string.next)) { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

}