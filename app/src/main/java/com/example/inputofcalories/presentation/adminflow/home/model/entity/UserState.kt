package com.example.inputofcalories.presentation.adminflow.home.model.entity

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserType

sealed class UserStatusChangeState {
    class UserUpgradeSucceed(newType: UserType): UserStatusChangeState()

    object UserUpgradeFailed: UserStatusChangeState()

    class UserDowngradeSucceed(newType: UserType): UserStatusChangeState()

    object UserDowngradeFailed: UserStatusChangeState()
}

sealed class UserLoadState {
    class UsersLoadSucceed(val users: List<User>): UserLoadState()

    object UsersLoadFailed: UserLoadState()

    object NoUsersFound: UserLoadState()
}