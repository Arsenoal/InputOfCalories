package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.domain.managerflow.MangerFlowUseCase
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

//TODO chage users list retrieve functionality(remove user id argument), move getUsers() method to ManagerViewModel(create it)
class UsersProviderViewModel(
    private val userId: String,
    private val getUsersUseCase: MangerFlowUseCase
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
        return getUsersUseCase.getUsers(userId)
    }
}