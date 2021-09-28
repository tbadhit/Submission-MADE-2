package com.tbadhit.submissionbfaa2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.tbadhit.submissionbfaa2.R
import com.tbadhit.submissionbfaa2.adapter.SectionsPagerAdapter
import com.tbadhit.submissionbfaa2.view_model.DetailViewModel
import com.tbadhit.submissionbfaa2.databinding.ActivityDetailBinding
import com.tbadhit.submissionbfaa2.helper.ViewModelFactory
import com.tbadhit.submissionbfaa2.transform.ZoomOutPageTransformer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.appbar)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        detailViewModel = obtainViewModel(this@DetailActivity)


        if (username != null) {
            detailViewModel.setDetailUser(username)
            detailViewModel.getDetailUser().observe(this, {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .apply(
                            RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.ic_account_circle)
                        )
                        .into(imgItemPhoto)

                    tvName.text = it.name ?: getString(R.string.name)
                    tvUsername.text = it.username
                    tvLocation.text = it.location ?: "-"
                    tvCompany.text = it.company ?: "-"
                    tvRepository.text = it.publicRepos.toString()
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()

                    var isFavorite = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val count = detailViewModel.checkUser(id)
                        withContext(Dispatchers.Main) {
                            if (count > 0) {
                                setStatusFavorite(true)
                                isFavorite = true
                            } else {
                                setStatusFavorite(false)
                            }
                        }
                    }

                    floatingActionButton.setOnClickListener {
                        isFavorite = !isFavorite

                        if (isFavorite) {
                            detailViewModel.addToFavorite(username, id, avatarUrl)
                            Toast.makeText(
                                this@DetailActivity,
                                getString(R.string.toast_add_favorite),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            detailViewModel.removeFromFavorite(id)
                            Toast.makeText(
                                this@DetailActivity,
                                getString(R.string.toast_remove_favorite),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        setStatusFavorite(isFavorite)
                    }
                }
            })
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            viewPager.setPageTransformer(ZoomOutPageTransformer())
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TITLE_TABS[position])
            }.attach()
        }
    }

    private fun setStatusFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_white)
        } else {
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        private val TITLE_TABS = intArrayOf(R.string.followers, R.string.following)
    }
}