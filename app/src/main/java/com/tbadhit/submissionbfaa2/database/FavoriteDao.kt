package com.tbadhit.submissionbfaa2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tbadhit.submissionbfaa2.model.Favorite

@Dao
interface FavoriteDao {
    @Insert
    fun insert(favorite: Favorite)

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    fun check(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * from favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>
}