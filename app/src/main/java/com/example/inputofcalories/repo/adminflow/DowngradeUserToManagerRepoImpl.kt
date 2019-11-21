package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class DowngradeUserToManagerRepoImpl(
    private val firestore: FirebaseFirestore
): DowngradeUserToManagerRepo {
    override suspend fun downgrade(userId: String) {
        firestore.collection(USERS).get()
            .addOnSuccessListener { usersQuerySnapshot ->
                usersQuerySnapshot.filter { documentSnapshot ->
                    userId == documentSnapshot.id
                }.map { documentSnapshot ->
                    val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                    val downgradedUser = UserFirebase(
                        id = userFirebase.id,
                        name = userFirebase.name,
                        email = userFirebase.email,
                        password = userFirebase.password,
                        dailyCalories = userFirebase.dailyCalories,
                        type = TYPE_MANAGER)

                    firestore.collection(USERS)
                        .document(userId)
                        .set(downgradedUser)
                        .addOnSuccessListener {  }
                        .addOnFailureListener { throw UserException(error = it) }
                }
            }
            .addOnFailureListener { throw UserException(error = it) }
    }
}