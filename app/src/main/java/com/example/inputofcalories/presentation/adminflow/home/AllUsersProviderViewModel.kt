package com.example.inputofcalories.presentation.adminflow.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.domain.adminflow.GetAllUsersUseCase
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel
import kotlinx.coroutines.launch

private const val GET_ALL_USERS_REQUEST_CODE = 1

class AllUsersProviderViewModel(
    private val userId: String,
    private val getAllUsersUseCase: GetAllUsersUseCase
): BaseViewModel(), HandleError {

    val usersLoadFailLiveData = MutableLiveData<Any>()

    val usersLoadSuccessLiveData = MutableLiveData<List<User>>()

    val noUsersFoundLiveData =  MutableLiveData<Any>()

    fun getUsers() {
        viewModelScope.launch {
            val users = loadUsers(userId)

            if(users.isNotEmpty()) usersLoadSuccessLiveData.value = users
            else noUsersFoundLiveData.value = Any()
            //print(users)
        }
    }

    private suspend fun loadUsers(userId: String) = getAllUsersUseCase.get(userId)

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_ALL_USERS_REQUEST_CODE -> { usersLoadFailLiveData.value = Any() }
        }
    }
}