package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.managerflow.GetUsersUseCase
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val GET_REGULAR_USERS_REQUEST_CODE = 1
const val GET_USER_REQUEST_CODE = 2

class UsersProviderViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserUseCase: GetUserUseCase
): BaseViewModel(), HandleError {

    val usersLoadFailLiveData = MutableLiveData<Message>()

    val usersLoadSuccessLiveData = MutableLiveData<List<User>>()

    val noUsersFoundLiveData =  MutableLiveData<Any>()

    fun getUsers() {
        getUser { user ->
            loadUsers(user.id) { users ->
                if (users.isEmpty()) noUsersFoundLiveData.value = Any()
                else usersLoadSuccessLiveData.value = users
            }
        }
    }

    private fun loadUsers(userId: String, success: Success<List<User>>) {
        execute(getUsersUseCase.get(userId),
            requestCode = GET_REGULAR_USERS_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    private fun getUser(success: Success<User>) {
        execute(getUserUseCase.get(),
            requestCode = GET_USER_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_REGULAR_USERS_REQUEST_CODE -> {
                usersLoadFailLiveData.value = Message("falied to load users")
            }
            GET_USER_REQUEST_CODE -> {
                //nothing for now
            }
        }
    }
}