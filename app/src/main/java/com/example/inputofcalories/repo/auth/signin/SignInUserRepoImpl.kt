package com.example.inputofcalories.repo.auth.signin

import com.example.inputofcalories.common.exception.SignInException
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserParams
import com.example.inputofcalories.entity.register.UserSignInParams
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single
import io.reactivex.SingleEmitter

class SignInUserRepoImpl(
    private val firestore: FirebaseFirestore): SignInUserRepo {

    override fun signIn(userSignInParams: UserSignInParams): Single<User> {
        return Single.create<Boolean> { emitter ->
            checkUserWithEmailPresentInDb(userSignInParams.email, emitter)
        }.flatMap { isUserWithEmailPresent ->
            if(isUserWithEmailPresent) {
                signInUser(userSignInParams)
            } else {
                throw SignInException(message = "user with provided email is not present in db")
            }
        }
    }

    private fun checkUserWithEmailPresentInDb(email: String, emitter: SingleEmitter<Boolean>) {
        val usersRef = firestore.collection(USERS)

        usersRef.get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.documents.forEach { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    userFirebase?.let {
                        if(it.email == email) emitter.onSuccess(true)
                    }
                }

                if (!emitter.isDisposed) emitter.onSuccess(false)
            }
            .addOnFailureListener { emitter.onSuccess(false) }
    }

    private fun signInUser(userSignInParams: UserSignInParams): Single<User> {
        return Single.create<User> { emitter ->
            val usersRef = firestore.collection(USERS)

            usersRef.get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.documents.forEach { documentSnapshot ->
                        val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                        userFirebase?.let {
                            if(it.email == userSignInParams.email && it.password == userSignInParams.password) {
                                val userParams = UserParams(
                                    name = it.name,
                                    email = it.email)

                                val user = User(
                                    id = documentSnapshot.id,
                                    userParams = userParams
                                )

                                emitter.onSuccess(user)
                            }
                        }
                    }

                    if(!emitter.isDisposed) emitter.onError(SignInException(message = "password mismatch"))
                }
                .addOnFailureListener {
                    if(!emitter.isDisposed) emitter.onError(SignInException(error = it, message = it.message))
                }
        }
    }
}