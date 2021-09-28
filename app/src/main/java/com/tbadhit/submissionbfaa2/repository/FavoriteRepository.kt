package com.tbadhit.submissionbfaa2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.tbadhit.submissionbfaa2.database.FavoriteDao
import com.tbadhit.submissionbfaa2.database.FavoriteRoomDatabase
import com.tbadhit.submissionbfaa2.model.Favorite

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun check(id: Int) = mFavoriteDao.check(id)

    fun insert(favorite: Favorite) = mFavoriteDao.insert(favorite)

    fun delete(id: Int) = mFavoriteDao.delete(id)
}