package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.repo.auth.registration.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class DowngradeUserToRegularRepoImpl(
    private val firestore: FirebaseFirestore
): DowngradeUserToRegularRepo {
    override fun downgrade(userId: String): Completable {
        return Completable.create { emitter ->
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
                            type = TYPE_REGULAR
                        )

                        firestore.collection(USERS)
                            .document(userId)
                            .set(downgradedUser)
                            .addOnSuccessListener { emitter.onComplete() }
                            .addOnFailureListener { emitter.onError(UserException(error = it)) }
                    }
                }
                .addOnFailureListener { emitter.onError(UserException(error = it)) }
        }
    }
}