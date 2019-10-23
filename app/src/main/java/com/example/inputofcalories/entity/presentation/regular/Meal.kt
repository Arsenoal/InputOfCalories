package com.example.inputofcalories.entity.presentation.regular

data class Meal(
    val id: String,
    val params: MealParams,
    val filterParams: MealFilterParams
) {
    override fun toString(): String {
        return "Meal: {$id params: $params}"
    }
}

data class MealParams(
    val text: String,
    val calories: String,
    val weight: String
) {
    override fun toString(): String {
        return "$text $calories $weight"
    }
}

data class MealFilterParams(
    var date: MealDateParams,
    var time: MealTimeParams
) {
    override fun toString(): String {
        return "\nMealFilterParams(date=$date, time=from: ${time.from}, to: ${time.to})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealFilterParams

        if (date != other.date) return false
        if (time.from != other.time.from || time.to != other.time.to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + time.from.hashCode()
        result = 31 * result + time.to.hashCode()
        return result
    }


}