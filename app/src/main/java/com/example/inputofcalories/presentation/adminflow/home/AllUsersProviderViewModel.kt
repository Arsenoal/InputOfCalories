package com.example.inputofcalories.presentation.adminflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.adminflow.GetAllUsersUseCase
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

private const val GET_ALL_USERS_REQUEST_CODE = 1
private const val GET_USER_REQUEST_CODE = 2

class AllUsersProviderViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getUserUseCase: GetUserUseCase
): BaseViewModel(), HandleError {

    val usersLoadFailLiveData = MutableLiveData<Message>()

    val usersLoadSuccessLiveData = MutableLiveData<List<User>>()

    val noUsersFoundLiveData =  MutableLiveData<Any>()

    fun getUsers() {
        getUser { user ->
            loadUsers(userId = user.id) { users ->
                if(users.isNotEmpty()) usersLoadSuccessLiveData.value = users
                else noUsersFoundLiveData.value = Any()
            }
        }
    }

    private fun loadUsers(userId: String, success: Success<List<User>>) {
        execute(getAllUsersUseCase.get(userId),
            requestCode = GET_ALL_USERS_REQUEST_CODE,
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
            GET_ALL_USERS_REQUEST_CODE -> {
                usersLoadFailLiveData.value = Message("falied to load users")
            }
            GET_USER_REQUEST_CODE -> {
                //retry maybe
            }
        }
    }
}