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

        holder.upgradeButton.setOnClickListener {
            userUpgradeSelectedLiveData.value = UserTypeChangeParams(user.id, position)
        }

        holder.downgradeButton.setOnClickListener {
            userDowngradeSelectedLiveData.value = UserTypeChangeParams(user.id, position)
        }
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
        private val typeTextView: AppCompatTextView = view.findViewById(R.id.typeTextView)

        val upgradeButton: AppCompatButton = view.findViewById(R.id.upgradeButton)
        val downgradeButton: AppCompatButton = view.findViewById(R.id.downgradeButton)

        fun bind(user: User) {
            userNameTextView.text = user.userParams.name
            emailTextView.text = user.userParams.email
            typeTextView.text = when(user.userParams.type) {
                RegularUser -> { itemView.context.getString(R.string.regular) }
                UserManager -> { itemView.context.getString(R.string.manager) }
                Admin -> { itemView.context.getString(R.string.admin) }
            }
        }
    }
}