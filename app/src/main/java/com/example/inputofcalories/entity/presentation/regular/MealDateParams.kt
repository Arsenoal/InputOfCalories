package com.example.inputofcalories.entity.presentation.regular

data class MealDateParams(
    val year: String,
    val month: String,
    val dayOfMonth: String
) {
    override fun toString(): String {
        return "\n(year='$year', month='$month', dayOfMonth='$dayOfMonth')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealDateParams

        if (year != other.year) return false
        if (month != other.month) return false
        if (dayOfMonth != other.dayOfMonth) return false

        return true
    }

    override fun hashCode(): Int {
        var result = year.hashCode()
        result = 31 * result + month.hashCode()
        result = 31 * result + dayOfMonth.hashCode()
        return result
    }


}