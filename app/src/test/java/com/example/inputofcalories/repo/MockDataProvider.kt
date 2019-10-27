package com.example.inputofcalories.repo

import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserParams

object MockDataProvider {

    //calories sum = 500
    val mealsList: List<Meal> = listOf(
        Meal(id = "96bc1c95-86f0-4cec-88c8-aa97335529b6",
            params = MealParams(
                text = "meal1",
                calories = "100",
                weight = "100"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "10",
                    dayOfMonth = "26"),
                time = LunchTime())),
        Meal(id = "52fcfe2a-393d-4a6c-97c5-7997b2d53e97",
            params = MealParams(
                text = "meal2",
                calories = "110",
                weight = "80"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "10",
                    dayOfMonth = "27"),
                time = LunchTime())),
        Meal(id = "885e400f-b4e4-4b7f-a5e8-a4259339fed2",
            params = MealParams(
                text = "meal3",
                calories = "130",
                weight = "120"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "10",
                    dayOfMonth = "27"),
                time = LunchTime())),
        Meal(id = "df9ab6a2-a140-4780-8a99-dd771a1dda68",
            params = MealParams(
                text = "meal4",
                calories = "150",
                weight = "40"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "10",
                    dayOfMonth = "27"),
                time = LunchTime())),
        Meal(id = "0eafd5e2-0a1b-405f-a540-41ed039cc0da",
            params = MealParams(
                text = "meal5",
                calories = "500",
                weight = "250"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "9",
                    dayOfMonth = "27"),
                time = LunchTime()))
    )

    val filteredMealsList: List<Meal> = listOf(
        Meal(id = "52fcfe2a-393d-4a6c-97c5-7997b2d53e97",
            params = MealParams(
                text = "meal2",
                calories = "110",
                weight = "80"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "10",
                    dayOfMonth = "27"),
                time = LunchTime())),
        Meal(id = "885e400f-b4e4-4b7f-a5e8-a4259339fed2",
            params = MealParams(
                text = "meal3",
                calories = "130",
                weight = "120"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "10",
                    dayOfMonth = "27"),
                time = LunchTime())),
        Meal(id = "df9ab6a2-a140-4780-8a99-dd771a1dda68",
            params = MealParams(
                text = "meal4",
                calories = "150",
                weight = "40"),
            filterParams = MealFilterParams(
                date = MealDateParams(
                    year = "2019",
                    month = "10",
                    dayOfMonth = "27"),
                time = LunchTime()))
    )

    val mealParams = MealParams(
        text = "my meal",
        calories = "400",
        weight = "100")

    val mealFilterParams = MealFilterParams(
        date = MealDateParams(
            year = "2019",
            month = "10",
            dayOfMonth = "26"),
        time = LunchTime()
    )

    val mealToEdit =  Meal(id = "96bc1c95-86f0-4cec-88c8-aa97335529b6",
        params = MealParams(
            text = "meal1",
            calories = "100",
            weight = "100"),
        filterParams = MealFilterParams(
            date = MealDateParams(
                year = "2019",
                month = "10",
                dayOfMonth = "26"),
            time = LunchTime()))

    val mealDeleteParams = MealDeleteParams(
        userId = "4449fc18-0210-4f09-adfd-ed216369a173",
        mealId = "96bc1c95-86f0-4cec-88c8-aa97335529b6"
    )

    //users

    val user = User(
        id = "4449fc18-0210-4f09-adfd-ed216369a173",
        userParams = UserParams(
            name = "Arsen",
            email = "arsen.budumyan@gmail.com",
            dailyCalories = "1200",
            type = RegularUser
        )
    )

    val usersList: List<User> = listOf(
        User(id = "4449fc18-0210-4f09-adfd-ed216369a173",
            userParams = UserParams(
                name = "Arsen",
                email = "arsen.budumyan@gmail.com",
                dailyCalories = "1200",
                type = RegularUser)),
        User(id = "04bec7f7-7533-4fd2-b9f4-d95204a65703",
            userParams = UserParams(
                name = "UserA",
                email = "userA@mail.com",
                dailyCalories = "100",
                type = RegularUser)),
        User(id = "30294bc8-8644-4425-af08-6fb0e7d04200",
            userParams = UserParams(
                name = "UserB",
                email = "userB@mail.com",
                dailyCalories = "1020",
                type = RegularUser)),
        User(id = "5bf70822-9e68-4dc5-903f-bec8221b1540",
            userParams = UserParams(
                name = "UserC",
                email = "userC@mail.com",
                dailyCalories = "102",
                type = RegularUser)),
        User(id = "7d8d170a-a095-445f-b554-871dd030751a",
            userParams = UserParams(
                name = "UserD",
                email = "userD@mail.com",
                dailyCalories = "65",
                type = RegularUser)),
        User(id = "c10a1c7f-bbb1-4b64-bf97-2e804da5c8fb",
            userParams = UserParams(
                name = "UserE",
                email = "userE@mail.com",
                dailyCalories = "659",
                type = RegularUser))
    )

}