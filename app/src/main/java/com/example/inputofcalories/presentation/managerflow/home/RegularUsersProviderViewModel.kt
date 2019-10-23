package com.example.inputofcalories.presentation.managerflow.home

import androidx.lifecycle.MutableLiveData
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.domain.managerflow.GetRegularUsersUseCase
import com.example.inputofcalories.entity.presentation.Message
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.presentation.viewModel.BaseViewModel

const val GET_REGULAR_USERS_REQUEST_CODE = 1

class RegularUsersProviderViewModel(
    private val getRegularUsersUseCase: GetRegularUsersUseCase
): BaseViewModel(), HandleError {

    val usersLoadFailLiveData = MutableLiveData<Message>()

    val usersLoadSuccessLiveData = MutableLiveData<List<User>>()

    val noUsersFoundLiveData =  MutableLiveData<Any>()

    fun getUsers() {
        loadUsers { users ->
            if (users.isEmpty()) noUsersFoundLiveData.value = Any()
            //TODO u know what I mean (:)
        }
    }

    fun loadUsers(success: Success<List<User>>) {
        execute(getRegularUsersUseCase.get(),
            requestCode = GET_REGULAR_USERS_REQUEST_CODE,
            handleError = this,
            success = success)
    }

    override fun invoke(t: Throwable, requestCode: Int?) {
        when(requestCode) {
            GET_REGULAR_USERS_REQUEST_CODE -> {
                usersLoadFailLiveData.value = Message("falied to load users")
            }
        }
    }
}