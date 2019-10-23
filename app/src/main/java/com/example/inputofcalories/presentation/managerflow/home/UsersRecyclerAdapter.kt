package com.example.inputofcalories.presentation.managerflow.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserManager

class UsersRecyclerAdapter(
    private val users: MutableList<User> = mutableListOf()
): RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

    lateinit var layoutInflater: LayoutInflater

    val userUpgradeSelectedLiveData: MutableLiveData<User> = MutableLiveData()

    val userDowngradeSelectedLiveData: MutableLiveData<User> = MutableLiveData()

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

        holder.upgradeButton?.setOnClickListener {
            userUpgradeSelectedLiveData.value = user
        }

        holder.downgradeButton?.setOnClickListener {
            userDowngradeSelectedLiveData.value = user
        }
    }

    fun setItems(items: List<User>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItem(item: User) {

    }

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val userNameTextView = view.findViewById<AppCompatTextView>(R.id.userNameTextView)
        private val emailTextView = view.findViewById<AppCompatTextView>(R.id.emailTextView)
        private val typeTextView = view.findViewById<AppCompatTextView>(R.id.typeTextView)

        val upgradeButton = view.findViewById<AppCompatButton>(R.id.upgradeButton)
        val downgradeButton = view.findViewById<AppCompatButton>(R.id.downgradeButton)

        fun bind(user: User) {
            userNameTextView.text = user.userParams.name
            emailTextView.text = user.userParams.email
            typeTextView.text = when(user.userParams.type) {
                RegularUser -> { "regular" }
                UserManager -> { "manager" }
                Admin -> { "admin" }
            }
        }
    }
}