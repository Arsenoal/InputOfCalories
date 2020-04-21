package com.example.inputofcalories.presentation.managerflow.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.presentation.managerflow.home.model.UserTypeChangeParams

class UsersRecyclerAdapter(
    private val users: MutableList<User> = mutableListOf()
): RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    val userUpgradeSelectedLiveData: MutableLiveData<UserTypeChangeParams> = MutableLiveData()

    val userDowngradeSelectedLiveData: MutableLiveData<UserTypeChangeParams> = MutableLiveData()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(layoutInflater.inflate(R.layout.user_recycler_item, parent, false))
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.bind(user)
    }

    fun setItems(items: List<User>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }

    fun updateUserStatus(userType: UserType, position: Int) {
        users[position].userParams.type = userType
        notifyItemChanged(position)
    }

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val userNameTextView: AppCompatTextView = view.findViewById(R.id.userNameTextView)
        private val emailTextView: AppCompatTextView = view.findViewById(R.id.emailTextView)

        fun bind(user: User) {
            userNameTextView.text = user.userParams.name
            emailTextView.text = user.userParams.email
            when(user.userParams.type) {
                RegularUser -> {
                    //TODO
                }
                UserManager -> {
                    //TODO
                }
                Admin -> {
                    //TODO
                }
            }
        }
    }
}