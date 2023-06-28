package com.example.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.adapters.UserAdapter
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.responses.ItemsItem
import com.example.githubuser.viewmodels.DetailViewModel
import kotlin.properties.Delegates


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    companion object {

        const val ARG_FOLLOW    = "follow"
        const val ARG_LOGIN     = "login"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    var position by Delegates.notNull<Int>()
    var login: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvFollow: RecyclerView = view.findViewById(R.id.rvFollow)
        val layoutManager = LinearLayoutManager(context)
        rvFollow.layoutManager = layoutManager

        arguments?.let {
            position = it.getInt(ARG_FOLLOW)
            login = it.getString(ARG_LOGIN)!!
        }

        val isFollower = position == 1

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
        detailViewModel.follUser(isFollower, login)
        detailViewModel.followList.observe(viewLifecycleOwner){followList -> setFollData(followList)}

        detailViewModel.isLoading.observe(viewLifecycleOwner){showLoading(it)}
    }

    private fun setFollData(followList: List<ItemsItem>) {
        val adapter = UserAdapter(followList)
        binding.rvFollow.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}