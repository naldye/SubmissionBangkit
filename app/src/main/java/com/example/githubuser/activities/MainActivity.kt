package com.example.githubuser.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.responses.ItemsItem
import com.example.githubuser.viewmodels.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[UserViewModel::class.java]

        mainViewModel.setLogin("a")

        with(binding) {
            searchBar.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchBar.queryHint = getString(R.string.search_bar)
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null)
                        mainViewModel.setLogin(query)
                    searchBar.clearFocus()
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        mainViewModel.setLogin(newText)
                    }
                    return false
                }
            })
        }
        mainViewModel.isLoading.observe(this){showLoading(it)}
        mainViewModel.isLoading.observe(this){clearRV(it)}
        mainViewModel.users.observe(this){users -> setUserData(users)}
        val layoutManager = LinearLayoutManager(this)
        binding.rvListUsers.layoutManager = layoutManager
    }

    private fun setUserData(users: List<ItemsItem>) {
        val adapter = UserAdapter(users)
        binding.rvListUsers.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun clearRV(isLoading: Boolean){
        if (isLoading) {
            binding.rvListUsers.visibility = View.GONE
        } else {
            binding.rvListUsers.visibility = View.VISIBLE
        }
    }
}