package com.example.inputofcalories.repo.registration.model

class UserFirebase(
    val name: String,
    val email: String,
    val password: String) {
    constructor(): this("", "", "")
}