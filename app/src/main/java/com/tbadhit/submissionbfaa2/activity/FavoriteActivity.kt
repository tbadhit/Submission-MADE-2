package com.tbadhit.submissionbfaa2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbadhit.submissionbfaa2.R
import com.tbadhit.submissionbfaa2.activity.DetailActivity.Companion.EXTRA_AVATAR
import com.tbadhit.submissionbfaa2.activity.DetailActivity.Companion.EXTRA_ID
import com.tbadhit.submissionbfaa2.activity.DetailActivity.Companion.EXTRA_USERNAME
import com.tbadhit.submissionbfaa2.adapter.ListUserAdapter
import com.tbadhit.submissionbfaa2.databinding.ActivityFavoriteBinding
import com.tbadhit.submissionbfaa2.helper.ViewModelFactory
import com.tbadhit.submissionbfaa2.model.Favorite
import com.tbadhit.submissionbfaa2.model.ResponseUser
import com.tbadhit.submissionbfaa2.view_model.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.appbar_favorite)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClick(data: ResponseUser) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(EXTRA_USERNAME, data.username)
                    it.putExtra(EXTRA_ID, data.id)
                    it.putExtra(EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = adapter
        }

        favoriteViewModel.getAllFavorites()?.observe(this, { favoriteList ->
            if (favoriteList != null) {
                val list = mapList(favoriteList)
                adapter.setListUser(list)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun mapList(listFavorites: List<Favorite>): ArrayList<ResponseUser> {
        val listUser = ArrayList<ResponseUser>()
        for (user in listFavorites) {
            val userMapped = ResponseUser(
                username = user.username,
                id = user.id,
                avatarUrl = user.avatarUrl,
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}