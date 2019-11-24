package com.example.inputofcalories.repo.auth.registration

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.common.logger.IOCLogger
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.repo.auth.registration.model.TYPE_REGULAR
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.db.FirebaseDataBaseCollectionNames.USERS
import com.example.inputofcalories.repo.auth.registration.model.UserFirebase
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.*
import kotlinx.coroutines.suspendCancellableCoroutine

class RegisterUserFirestore(
    private val firestore: FirebaseFirestore,
    private val uuidGeneratorService: UUIDGeneratorService
): RegisterUserRepo {

    override suspend fun register(userRegistrationParams: UserRegistrationParams) {

        val usersRef = firestore.collection(USERS)

        val uId = uuidGeneratorService.get().toString()

        val userFirebase = UserFirebase(
            id = uId,
            name = userRegistrationParams.name,
            email = userRegistrationParams.email,
            password = userRegistrationParams.password,
            dailyCalories = userRegistrationParams.dailyCalories,
            type = TYPE_REGULAR)

        usersRef.document(uId).set(userFirebase)
            .addOnFailureListener { throw RegistrationException(error = it) }
    }
}