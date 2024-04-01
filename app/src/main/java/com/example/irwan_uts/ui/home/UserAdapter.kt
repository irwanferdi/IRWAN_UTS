package com.example.irwan_uts.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.irwan_uts.model.DataItemUser
import com.example.irwan_uts.R

class UserAdapter(private var users: ArrayList<DataItemUser>) :
    RecyclerView.Adapter<UserAdapter.ListUserViewHolder>() {

    fun setFilteredList(users: ArrayList<DataItemUser>){
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListUserViewHolder(view)
    }

    fun addUser(newUsers: DataItemUser) {
        users.add(newUsers)
        notifyItemInserted(users.lastIndex)
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val user = users[position]

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(80, 80).placeholder(R.drawable.icon))
            .transform(CircleCrop())
            .into(holder.imageUser)

        holder.textUsernameUser.text = "${user.firstName} ${user.lastName}"
        holder.textEmailUser.text = user.email
    }

    class ListUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textUsernameUser: TextView = itemView.findViewById(R.id.text_username_user)
        var textEmailUser: TextView = itemView.findViewById(R.id.text_email_user)
        var imageUser: ImageView = itemView.findViewById(R.id.image_user)

    }
}