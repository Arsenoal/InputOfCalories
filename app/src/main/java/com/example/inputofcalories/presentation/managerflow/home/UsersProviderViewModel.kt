package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.domain.managerflow.GetUsersUseCase
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class UsersProviderViewModel(
    private val userId: String,
    private val getUsersUseCase: GetUsersUseCase
): BaseViewModel() {

    val usersLoadFailLiveData = MutableLiveData<Any>()

    val usersLoadSuccessLiveData = MutableLiveData<List<User>>()

    val noUsersFoundLiveData =  MutableLiveData<Any>()

    fun getUsers() {
        viewModelScope.launch {
            try {
                val users = loadUsers(userId)

                if (users.isEmpty()) noUsersFoundLiveData.value = Any()
                else usersLoadSuccessLiveData.value = users
            } catch (ex: UserException) {
                usersLoadFailLiveData.value = Any()
            }
        }
    }

    private suspend fun loadUsers(userId: String): List<User> {
        return getUsersUseCase.get(userId)
    }
}