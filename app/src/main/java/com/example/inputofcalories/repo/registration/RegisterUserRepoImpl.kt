package com.example.inputofcalories.repo.registration

import com.example.inputofcalories.common.exception.RegistrationException
import com.example.inputofcalories.common.logger.IOFLogger
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserRegistrationParams
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.db.FirebaseDataBaseProps.USERS
import com.example.inputofcalories.repo.registration.model.UserFirebase
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.*

class RegisterUserRepoImpl(
    private val firestore: FirebaseFirestore,
    private val uuidGeneratorService: UUIDGeneratorService
): RegisterUserRepo {

    override fun register(userRegistrationParams: UserRegistrationParams): Completable {
        return Single.create<Boolean> { emitter ->
            checkUserPersistInDb(userRegistrationParams.email, emitter)
        }.flatMapCompletable {
            if(it) addUser(userRegistrationParams)
            else throw RegistrationException()
        }
    }

    private fun checkUserPersistInDb(email: String, emitter: SingleEmitter<Boolean>) {
            val usersRef = firestore.collection(USERS)
            usersRef.document(email)
            usersRef
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.documents.forEach { documentSnapshot ->
                        val userFirebase = documentSnapshot.toObject(UserFirebase::class.java)

                        userFirebase?.let {
                            if (it.email == email) emitter.onSuccess(false)
                        }

                        emitter.onSuccess(true)

                    }
                }
                .addOnFailureListener { emitter.onSuccess(false) }
    }

    private fun addUser(userRegistrationParams: UserRegistrationParams): Completable {
        return Completable.create { emitter ->
            val usersRef = firestore.collection(USERS)
            val uId = uuidGeneratorService.get().toString()
            val userFirebase = UserFirebase(
                name = userRegistrationParams.name,
                email = userRegistrationParams.email,
                password = userRegistrationParams.password)
            usersRef.document(uId).set(userFirebase)
                .addOnSuccessListener {
                    IOFLogger.d(RegisterUserRepoImpl::class.java.name, "DocumentSnapshot successfully written!")
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}