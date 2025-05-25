package repository.impl

import database.UserQueries
import migrations.User_user
import model.User
import repository.UserUserRepository
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime


class UserUserRepositoryImpl (
    private val queries: UserQueries
) : UserUserRepository{
    override fun getById(authId: Int): User? {
        val user: User_user = queries.getByAuthId(authId).executeAsOneOrNull() ?: return null

        return User(
            id = user.id,
            authID = user.auth_id,
            sex = user.sex,
            birthday = user.birthday,
            dietType = user.diet_type,
            breakfastTime = user.breakfast_time,
            lunchTime = user.lunch_time,
            dinnerTime = user.dinner_time,
        )
    }

    override fun create(
        authId: Int,
        sex: String,
        birthday: LocalDateTime?,
        dietType: String?,
        breakfastTime: LocalDateTime?,
        lunchTime: LocalDateTime?,
        dinnerTime: LocalDateTime?
    ) {
        TODO("Not yet implemented")
    }


}