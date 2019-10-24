package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

class GetAllUsersRepoImpl(
    private val firestore: FirebaseFirestore
): GetAllUsersRepo {
    override fun get(userId: String): Single<List<User>> {
        return Single.create<List<User>> { emitter ->
            firestore.collection(USERS).get()
                .addOnSuccessListener { usersQuery ->
                    val users: List<User> = usersQuery.documents
                        .asSequence()
                        .filter {
                            val userFirebase = it.toObject(UserFirebase::class.java)

                            var userType = -1

                            userFirebase?.let { user->
                                userType = user.type
                            }

                            userId != it.id
                        }
                        .map { documentSnapshot ->
                            val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                            val type: UserType = when(userFirebase?.type ?: String.empty()) {
                                TYPE_MANAGER -> { UserManager }
                                TYPE_ADMIN -> { Admin }
                                else -> { RegularUser }
                            }

                            val userParams = UserParams(
                                name = userFirebase?.name ?: String.empty(),
                                email = userFirebase?.email ?: String.empty(),
                                type = type)

                            val user = User(
                                documentSnapshot.id,
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