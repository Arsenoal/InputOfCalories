package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

class GetUsersRepoImpl(
    private val firestore: FirebaseFirestore
): GetUsersRepo {
    override fun get(): Single<List<User>> {
        return Single.create<List<User>> { emitter ->
            firestore.collection(USERS).get()
                .addOnSuccessListener { usersQuery ->
                    val users: List<User> = usersQuery.documents
                        .asSequence()
                        .filter {
                            val userFirebase = it.toObject(UserFirebase::class.java)
                            var userType = -1

                            userFirebase?.let { userType = it.type }

                            userType == TYPE_REGULAR || userType == TYPE_MANAGER
                        }
                        .map {
                            val userFirebase = it.toObject(UserFirebase::class.java)
                            userFirebase
                        }
                        .filterNotNull()
                        .map {
                            val type: UserType = when(it.type) {
                                TYPE_MANAGER -> { UserManager }
                                TYPE_ADMIN -> { Admin }
                                else -> { RegularUser }
                            }

                            val userParams = UserParams(
                                name = it.name,
                                email = it.email,
                                type = type)

                            val user = User(
                                it.id,
                                userParams)

                            user
                        }
                        .toList()

                    emitter.onSuccess(users)
                }
                .addOnFailureListener { emitter.onError(UserException(it)) }
        }
    }
}