package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.common.exception.UserException
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class UpgradeUserToMangerRepoImpl(
    private val firestore: FirebaseFirestore
): UpgradeUserToMangerRepo {
    override fun upgrade(userId: String): Completable {
        return Completable.create { emitter ->
            firestore.collection(FirebaseDataBaseCollectionNames.USERS).get()
                .addOnSuccessListener { usersQuerySnapshot ->
                    usersQuerySnapshot.filter { documentSnapshot ->
                        userId == documentSnapshot.id
                    }.map { documentSnapshot ->
                        val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                        val upgradedUser = UserFirebase(
                            id = userFirebase.id,
                            name = userFirebase.name,
                            email = userFirebase.email,
                            password = userFirebase.password,
                            type = TYPE_MANAGER
                        )

                        documentSnapshot.reference.collection(FirebaseDataBaseCollectionNames.USERS)
                            .document(userId)
                            .set(upgradedUser)
                            .addOnSuccessListener { emitter.onComplete() }
                            .addOnFailureListener { emitter.onError(UserException(error = it)) }
                    }
                }
                .addOnFailureListener { emitter.onError(UserException(error = it)) }
        }
    }
}