package com.example.inputofcalories.presentation.adminflow.home.model.entity

import com.example.inputofcalories.entity.register.User

sealed class UserStatusChangeState {
    object UserStatusChangeSucceed: UserStatusChangeState()

    object UserStatusChangeFailed: UserStatusChangeState()
}

sealed class UserLoadState {
    class UsersLoadSucceed(val users: List<User>): UserLoadState()

    object UsersLoadFailed: UserLoadState()

    object NoUsersFound: UserLoadState()
}