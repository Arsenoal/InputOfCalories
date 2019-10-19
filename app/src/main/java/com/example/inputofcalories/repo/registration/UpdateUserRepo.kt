package com.example.inputofcalories.repo.registration

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserUpdateParams
import io.reactivex.Single

interface UpdateUserRepo {
    fun update(userUpdateParams: UserUpdateParams): Single<User>
}