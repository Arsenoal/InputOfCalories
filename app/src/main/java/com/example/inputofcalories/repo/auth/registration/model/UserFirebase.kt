package com.example.inputofcalories.repo.auth.registration.model

class UserFirebase(
    val name: String,
    val email: String,
    val password: String) {
    constructor(): this("", "", "")
}