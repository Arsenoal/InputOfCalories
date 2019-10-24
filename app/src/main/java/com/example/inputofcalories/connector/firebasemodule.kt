package com.example.inputofcalories.connector

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val firebasemodule = module {
    single { FirebaseFirestore.getInstance() }
}