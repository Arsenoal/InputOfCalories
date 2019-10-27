package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.managerflow.GetUsersUseCase
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

private const val GET_REGULAR_USERS_REQUEST_CODE = 1
private const val GET_USER_REQUEST_CODE = 2

class UsersProviderViewModel(
    private val userId: String,
    private val getUsersUseCase: GetUsersUseCase
): BaseViewModel(), HandleError {

    val usersLoadFailLiveData = MutableLiveData<Any>()

    val usersLoadSuccessLiveData = MutableLiveData<List<User>>()

    val noUsersFoundLiveData =  MutableLiveData<Any>()

    fun getUsers() {
        loadUsers(userId) { users ->
            if (users.isEmpty()) noUsersFoundLiveData.value = Any()
            else usersLoadSuccessLiveData.value = users
        }
    }

    private fun loadUsers(userId: String, success: Success<List<User>>) {
        execute(getUsersUseCase.get(userId),
            requestCode = GET_REGULAR_USERS_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_REGULAR_USERS_REQUEST_CODE -> {
                usersLoadFailLiveData.value = Any()
            }
            GET_USER_REQUEST_CODE -> {
                //retry maybe
            }
        }
    }
}