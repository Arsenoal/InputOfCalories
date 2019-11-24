package com.example.inputofcalories.presentation.adminflow.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.domain.adminflow.GetAllUsersUseCase
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class AllUsersProviderViewModel(
    private val userId: String,
    private val getAllUsersUseCase: GetAllUsersUseCase
): BaseViewModel() {

    val usersLoadFailLiveData = MutableLiveData<Any>()

    val usersLoadSuccessLiveData = MutableLiveData<List<User>>()

    val noUsersFoundLiveData =  MutableLiveData<Any>()

    fun getUsers() {
        viewModelScope.launch {
            try {
                val users = loadUsers(userId)

                if(users.isNotEmpty()) usersLoadSuccessLiveData.value = users
                else noUsersFoundLiveData.value = Any()
            } catch (ex: UserException) {
                usersLoadFailLiveData.value = Any()
            }
        }
    }

    private suspend fun loadUsers(userId: String) = getAllUsersUseCase.get(userId)
}