package com.example.inputofcalories.repo.registration

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserUpdateParams
import com.example.inputofcalories.repo.registration.mapper.firebaseUserToUserMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Single

class UpdateUserRepoImpl(
    private val auth: FirebaseAuth
): UpdateUserRepo {
    override fun update(userUpdateParams: UserUpdateParams): Single<User> {
        return Single.create<FirebaseUser> { emitter ->
            val profileUpdate = UserProfileChangeRequest
                .Builder()
                .setDisplayName(userUpdateParams.name)
                .build()

            auth.currentUser?.run {
                updateProfile(profileUpdate).addOnCompleteListener { updateTask ->
                    if (!updateTask.isSuccessful) if(!emitter.isDisposed) emitter.onError(
                        RegistrationException()
                    )
                    else emitter.onSuccess(this)
                }.addOnFailureListener { updateException ->
                    if(!emitter.isDisposed) emitter.onError(RegistrationException(message = updateException.message))
                }
            }
        }.map {
            auth.currentUser?.let {
                firebaseUserToUserMapper.map(it)
            }
        }

    }
}