package com.demo.android.presentation.utility



import com.demo.android.data.models.User
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

class PrefKeys {
    companion object {
        const val PermissionLocation = "permission_location"
        const val UserProfile = "userprofile"
        const val IsLogin = "IsLogin"
        const val AuthKey = "AuthKey"
        const val DeviceToken = "DeviceToken"
        const val PushTokenKey = "push_token_key"

        const val Latitude = "latitude"
        const val Longitude = "longitude"
        const val AndroidId = AppConstant.vDeviceUniqueId

        fun isUserLoggedIn(): Boolean {
            return getAuthKey().isNotEmpty()
        }

        fun getAuthKey(): String {
            return Prefs.getString(AuthKey, "")
        }

        fun setUser(user: User) {
            Prefs.putString(PrefKeys.UserProfile, Gson().toJson(user))
            Prefs.putString(PrefKeys.AuthKey, user?.authKey)
        }

        fun getUserCommon(): User? {
            val userJson = Prefs.getString(UserProfile, "")
            return if (null != userJson) Gson().fromJson<User>(userJson, User::class.java) else null
        }

        fun getFirebasePushToken(): String {
            return Prefs.getString(PushTokenKey, "")
        }

        fun getLatitude(): String {
            return Prefs.getString(Latitude, "")
        }

        fun getLongitude(): String {
            return Prefs.getString(Longitude, "")
        }

        fun getLanguageSelectedCapital(): String {
            val language = Prefs.getString(LocaleHelper.SELECTED_LANGUAGE, "en")
            //return language.toUpperCase()
            return language
        }

        fun getAndroidId(): String {
            return Prefs.getString(AndroidId, "")
        }
    }
}