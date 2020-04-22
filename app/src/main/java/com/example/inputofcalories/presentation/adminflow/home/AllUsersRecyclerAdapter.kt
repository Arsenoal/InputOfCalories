package com.example.inputofcalories.presentation.adminflow.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.inputofcalories.R
import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.presentation.adminflow.home.model.UserTypeChangeParams
import com.google.android.material.button.MaterialButtonToggleGroup

const val UNCHECKED_BUTTON_ID = -1

class AllUsersRecyclerAdapter(
    private val users: MutableList<User> = mutableListOf()
): RecyclerView.Adapter<AllUsersRecyclerAdapter.UserViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    val userStatusChangeSelectedLiveData = MutableLiveData<UserTypeChangeParams>()

    val userSelectedLiveData = MutableLiveData<String>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = UserViewHolder(layoutInflater.inflate(R.layout.user_recycler_item, parent, false))

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.bind(user)

        holder.itemView.setOnClickListener { userSelectedLiveData.value = user.id }

        holder.typeToggleGroup.addOnButtonCheckedListener { group, _, isChecked ->
            when(group.checkedButtonId) {
                R.id.typeRegular -> { if (!isChecked) { userStatusChangeSelectedLiveData.value = UserTypeChangeParams(user.id, RegularUser, position) } }
                R.id.typeManager -> { if(!isChecked) { userStatusChangeSelectedLiveData.value = UserTypeChangeParams(user.id, UserManager, position) } }
                R.id.typeAdmin -> { userStatusChangeSelectedLiveData.value = UserTypeChangeParams(user.id, Admin, position) }
                UNCHECKED_BUTTON_ID -> { holder.bind(user) }
            }
        }
    }

    fun setItems(items: List<User>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val userNameTextView: AppCompatTextView = view.findViewById(R.id.userNameTextView)
        private val emailTextView: AppCompatTextView = view.findViewById(R.id.emailTextView)
        val typeToggleGroup: MaterialButtonToggleGroup = view.findViewById(R.id.typeToggleGroup)

        fun bind(user: User) {
            userNameTextView.text = user.userParams.name
            emailTextView.text = user.userParams.email

            when(user.userParams.type) {
                RegularUser -> { typeToggleGroup.check(R.id.typeRegular) }
                UserManager -> { typeToggleGroup.check(R.id.typeManager) }
                Admin -> { typeToggleGroup.check(R.id.typeAdmin) }
            }
        }
    }
}