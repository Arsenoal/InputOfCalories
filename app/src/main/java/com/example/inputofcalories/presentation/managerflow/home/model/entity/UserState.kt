package com.example.inputofcalories.presentation.managerflow.home.model.entity

import com.example.inputofcalories.entity.register.User

sealed class UserStatusChangeState {
    object UserUpgradeSucceed: UserStatusChangeState()

    object UserUpgradeFailed: UserStatusChangeState()

    object UserDowngradeSucceed: UserStatusChangeState()

    object UserDowngradeFailed: UserStatusChangeState()
}

sealed class UserLoadState {
    class UsersLoadSucceed(val users: List<User>): UserLoadState()

    object UsersLoadFailed: UserLoadState()

    object NoUsersFound: UserLoadState()
}