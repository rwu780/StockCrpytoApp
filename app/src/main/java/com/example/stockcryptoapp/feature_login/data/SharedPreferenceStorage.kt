package com.example.stockcryptoapp.feature_login.data

import android.content.Context
import com.example.stockcryptoapp.feature_login.domain.User
import com.example.stockcryptoapp.feature_login.domain.UserStorage
import javax.inject.Inject


class SharedPreferenceStorage @Inject constructor(
    context: Context

) : UserStorage {

    private val SHARED_PREFERENCE_KEY = "com.example.stockcryptoapp.preference"

    private val USERNAME = "username"
    private val FAVORIATE = "user_favorite"

    private val sharedPreferenceStorage = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    override fun updateUser(user: User?) {
        user?.let {
            val key = generateUserFavoriteListKey(user.username)

            with(sharedPreferenceStorage.edit()){
                putStringSet(key, user.favoriteSymbols)
                apply()
            }
        }
    }

    override fun retrieveUser(username: String, password: String): User? {

        return if (isValidUser(username)){
            when(passwordMatched(username, password)){
                false -> null
                true -> {
                    val userFavorite = sharedPreferenceStorage.getStringSet(
                        generateUserFavoriteListKey(username),
                        null
                    )

                    User(username, userFavorite)
                }
            }
        } else null
    }


    override fun isValidUser(username: String): Boolean {
        val key = generateUsernamePasswordKey(username)

        return !sharedPreferenceStorage.getString(key, "").isNullOrBlank()

    }

    override fun registerUser(username: String, password: String): User? {
        if (isValidUser(username))
            return null

        val key = generateUsernamePasswordKey(username)
        with(sharedPreferenceStorage.edit()){
            putString(key, password)
            apply()
        }
        return User(username)
    }

    private fun passwordMatched(username: String, password: String): Boolean {
        val key = generateUsernamePasswordKey(username)
        val storedPassword = sharedPreferenceStorage.getString(key, "")

        return storedPassword == password

    }

    private fun generateUserFavoriteListKey(username: String) =
        "$SHARED_PREFERENCE_KEY.$FAVORIATE.$username"

    private fun generateUsernamePasswordKey(username: String) = "$SHARED_PREFERENCE_KEY.$USERNAME.${username}"


}