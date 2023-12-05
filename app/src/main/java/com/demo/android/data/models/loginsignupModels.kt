package com.demo.android.data.models

import android.os.Parcelable
import com.demo.android.presentation.utility.AppConstant
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RegisterUserPRQ(
    @SerializedName(AppConstant.language) var language: String? = "",
    @SerializedName(AppConstant.user_type) var user_type: String? = "",
    @SerializedName(AppConstant.fullname) var fullname: String? = "",
    @SerializedName(AppConstant.phone) var phone: String? = "",
    @SerializedName(AppConstant.email) var email: String? = "",
    @SerializedName(AppConstant.password) var password: String? = "",
    @SerializedName(AppConstant.timezone_detail) var timezone_detail: String? = "",
    @SerializedName(AppConstant.app_version) var app_version: String? = "",
    @SerializedName(AppConstant.device_type) var device_type: String? = "",
    @SerializedName(AppConstant.device_token) var device_token: String? = "",
    @SerializedName(AppConstant.device_name) var device_name: String? = "",
    @SerializedName(AppConstant.latitude) var latitude: String? = "",
    @SerializedName(AppConstant.longitude) var longitude: String? = "",
    @SerializedName(AppConstant.profile_pic) var profile_pic: String? = ""
) : Parcelable

@Parcelize
data class UpdateProfilePRQ(
    @SerializedName(AppConstant.language) var language: String? = "",
    @SerializedName(AppConstant.fullname) var fullname: String? = "",
    @SerializedName(AppConstant.profile_pic) var profile_pic: String? = ""
) : Parcelable

data class RegisterUserRS(
    @SerializedName("data")
    var user: User? = null
) : BaseResponse()

data class User(
    @SerializedName(AppConstant.user_id) var userId: String? = "",
    @SerializedName(AppConstant.fullname) var fullname: String? = "",
    @SerializedName(AppConstant.user_type) var userType: String? = "",
    @SerializedName(AppConstant.login_type) var loginType: String? = "",
    @SerializedName(AppConstant.social_id) var socialId: String? = "",
    @SerializedName(AppConstant.phone) var phone: String? = "",
    @SerializedName(AppConstant.email) var email: String? = "",
    @SerializedName(AppConstant.profile_pic) var profilePic: String? = "",
    @SerializedName(AppConstant.enable_push_notifications) var enablePushNotifications: String? = "",
    @SerializedName(AppConstant.latitude) var latitude: String? = "",
    @SerializedName(AppConstant.longitude) var longitude: String? = "",
    @SerializedName(AppConstant.email_verification_status) var emailVerificationStatus: String? = "",
    @SerializedName(AppConstant.phone_verification_status) var phoneVerificationStatus: String? = "",
    @SerializedName(AppConstant.is_online) var isOnline: String? = "",
    @SerializedName(AppConstant.is_active) var isActive: String? = "",
    @SerializedName(AppConstant.timezone_detail) var timezoneDetail: String? = "",
    @SerializedName(AppConstant.referral_code) var referralCode: String? = "",
    @SerializedName(AppConstant.authKey) var authKey: String? = "",
    @SerializedName(AppConstant.socket_id) var socketId: String? = "",
    @SerializedName(AppConstant.app_version) var appVersion: String? = "",
    @SerializedName(AppConstant.device_type) var deviceType: String? = "",
    @SerializedName(AppConstant.device_token) var deviceToken: String? = "",
    @SerializedName(AppConstant.device_name) var deviceName: String? = "",
    @SerializedName(AppConstant.language) var language: String? = ""
) {
    fun isPhoneVerified(): Boolean {
        return phoneVerificationStatus == AppConstant.Yes
    }

    fun isEmailVerified(): Boolean {
        return emailVerificationStatus == AppConstant.Yes
    }

    fun isUserLoggedIn(): Boolean {
        return authKey?.isNotEmpty() == true
    }
}


data class OtpVerificationPRQ(
    @SerializedName(AppConstant.authKey) var authKey: String,
    @SerializedName(AppConstant.iPhoneVerificationCode) var iPhoneVerificationCode: String,
)

data class ResendOtpPRQ(
    @SerializedName(AppConstant.authKey) var authKey: String,
)

data class AutoLoginPRQ(
    @SerializedName(AppConstant.authKey) var vAuthKey: String,
    @SerializedName(AppConstant.app_version) var appVersion: String,
    @SerializedName(AppConstant.device_token) var vDeviceToken: String,
    @SerializedName(AppConstant.vTimezone) var vTimezone: String,
    @SerializedName(AppConstant.vDeviceUniqueId) var vDeviceUniqueId: String

)

data class ForgotPasswordPRQ(
    @SerializedName(AppConstant.email) var email: String? = ""
)

data class LoginPRQ(
    @SerializedName(AppConstant.email) var email: String? = "",
    @SerializedName(AppConstant.password) var password: String? = "",
    @SerializedName(AppConstant.app_version) var app_version: String? = "",
    @SerializedName(AppConstant.device_type) var device_type: String? = "",
    @SerializedName(AppConstant.device_token) var device_token: String? = "",
    @SerializedName(AppConstant.device_name) var device_name: String? = ""
)

data class SendOtpPRQ(
    @SerializedName("vMobile") var vMobile: String? = ""
)

data class SendOtpRS(
    @SerializedName("data")
    var sendOtp: SendOtp? = null
) : BaseResponse()

@Parcelize
data class SendOtp(
    @SerializedName("otp")
    var otp: String? = ""
) : Parcelable

data class ChangePasswordPRQ(
    @SerializedName(AppConstant.authKey) var authKey: String,
    @SerializedName(AppConstant.oldPassword) var oldPassword: String,
    @SerializedName(AppConstant.newPassword) var newPassword: String
)

data class ResetPasswordPRQ(
    @SerializedName(AppConstant.email) var email: String,
    @SerializedName(AppConstant.password) var password: String,
    @SerializedName(AppConstant.otp) var otp: String
)

data class ResetPasswordRS(
    @SerializedName("data") var user: User? = null
) : BaseResponse()