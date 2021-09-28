package com.tbadhit.submissionbfaa2.activity

import com.tbadhit.submissionbfaa2.view_model.SearchViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbadhit.submissionbfaa2.R
import com.tbadhit.submissionbfaa2.adapter.ListUserAdapter
import com.tbadhit.submissionbfaa2.databinding.ActivityMainBinding
import com.tbadhit.submissionbfaa2.helper.ViewModelFactory
import com.tbadhit.submissionbfaa2.model.ResponseUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ListUserAdapter
    private lateinit var searchViewModel: SearchViewModel
    private var isChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.appbar)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClick(data: ResponseUser) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }

        })

        searchViewModel = obtainViewModel(this@MainActivity)

        searchViewModel.getThemeSetting().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        searchUser("a")

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchUser(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()) {
                        searchUser("a")

                    } else {
                        searchUser(newText)
                    }
                    return false
                }
            })
        }

        searchViewModel.getSearchUser().observe(this, {
            if (it != null) {
                adapter.setListUser(it)
                showLoading(false)
            }
        })
    }

    private fun searchUser(query: String) {
        showLoading(true)
        searchViewModel.setSearchUser(query)
    }

    private fun obtainViewModel(activity: AppCompatActivity): SearchViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SearchViewModel::class.java)
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (isChecked) {
            menu.findItem(R.id.theme_mode).setIcon(R.drawable.ic_light_mode)
        } else {
            menu.findItem(R.id.theme_mode).setIcon(R.drawable.ic_dark_mode)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.theme_mode -> {
                isChecked = !isChecked
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle(getString(R.string.alert_title))
                builder.setMessage(getString(R.string.alert_message))
                builder.setPositiveButton(getString(R.string.alert_positive)) { _, _ ->
                    searchViewModel.saveThemeSetting(isChecked)
                }
                builder.setNegativeButton(getString(R.string.alert_negative)) { dialog, _ ->
                    dialog.cancel()
                }
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


