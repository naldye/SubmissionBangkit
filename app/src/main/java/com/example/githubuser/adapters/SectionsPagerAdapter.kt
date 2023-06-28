package com.example.githubuser.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity, var login: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        val fragment = FollowFragment()

        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_FOLLOW, position + 1)
            putString(FollowFragment.ARG_LOGIN, login)
        }

        return fragment
    }

    override fun getItemCount(): Int {

        return 2

    }

}