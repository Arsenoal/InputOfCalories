package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.repo.auth.registration.model.TYPE_REGULAR
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class DowngradeManagerUserRepoImpl(
    private val firestore: FirebaseFirestore
): DowngradeManagerUserRepo {
    override fun downgrade(userId: String): Completable {
        return Completable.create { emitter ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
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

                        documentSnapshot.reference.collection(FirebaseDataBaseCollectionNames.USERS)
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