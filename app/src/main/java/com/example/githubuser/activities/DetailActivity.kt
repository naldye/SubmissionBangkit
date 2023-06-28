package com.example.githubuser.activities

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.responses.DetailUserResponse
import com.example.githubuser.viewmodels.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    companion object {
        @StringRes
        private val TAB_NAMES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = findViewById(R.id.vpFollow)

        var login: String = intent.getStringExtra("login")!!
        val sectionsPagerAdapter = SectionsPagerAdapter(this, login)
        viewPager.adapter = sectionsPagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tlFollow)
        TabLayoutMediator(tabLayout, viewPager)
        {tab, position -> tab.text = resources.getString(TAB_NAMES[position])}
            .attach()

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
        detailViewModel.setLogin(login)
        detailViewModel.detailUser.observe(this){detailUser -> setDetailData(detailUser)}

        //val layoutManager = LinearLayoutManager(this)
        //binding.rvFollow.layoutManager = layoutManager
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        detailViewModel.isLoading.observe(this){showLoading(it)}
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDetailData(detailUser: DetailUserResponse) {

        Glide
            .with(this)
            .load(detailUser.avatarUrl)
            .into(binding.ivUserImage)

        binding.tvUserId.text = detailUser.login
        binding.tvUserDisplayName.text = detailUser.name
        binding.tvFollowers.text = getString(R.string.followers_count, detailUser.followers)
        binding.tvFollowing.text = getString(R.string.following_count, detailUser.following)

    }

}