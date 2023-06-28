package com.example.githubuser.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.activities.DetailActivity
import com.example.githubuser.responses.ItemsItem
import de.hdodenhof.circleimageview.CircleImageView


class UserAdapter(private val listUser : List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val (login, pict) = listUser[position]
        holder.tvUserName.text = login
        Glide.with(holder.itemView)
            .load(pict)
            .error(R.drawable.goldstar)
            .into(holder.ivUserPicture)

        holder.itemView.setOnClickListener(){
            val detailContext   = holder.itemView.context
            val intentDetail    = Intent(detailContext, DetailActivity::class.java)
            intentDetail.putExtra("login", listUser[holder.adapterPosition].login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName    : TextView        = view.findViewById(R.id.tvUserName)
        val ivUserPicture : CircleImageView = view.findViewById(R.id.ivUserPicture)
    }

}