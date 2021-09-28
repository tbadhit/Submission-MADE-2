package com.tbadhit.submissionbfaa2.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tbadhit.submissionbfaa2.model.Favorite
import com.tbadhit.submissionbfaa2.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository?

    init {
        mFavoriteRepository = FavoriteRepository(application)
    }

    fun getAllFavorites(): LiveData<List<Favorite>>? = mFavoriteRepository?.getAllFavorites()
}