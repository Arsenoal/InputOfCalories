package com.example.inputofcalories.repo.auth.registration.model

class UserFirebase(
    val name: String,
    val email: String,
    val password: String,
    val mealIds: MealIds) {
    constructor(): this("", "", "", MealIds(""))
}

class MealIds(
    val id: String) {
    constructor(): this("")
}