package com.demo.android.domain.network

import com.demo.android.data.models.BaseResponse
import com.demo.android.data.models.RegisterUserRS
import com.demo.android.data.models.ResetPasswordRS
import com.demo.android.presentation.utility.ApiConstant
import com.demo.android.presentation.utility.AppConstant
import com.demo.android.presentation.utility.PrefKeys
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface LoginSignupApiService {

    @FormUrlEncoded
    @POST(ApiConstant.ApiLogin)
    suspend fun login(
        @Field(AppConstant.email) vEmail: String,
        @Field(AppConstant.password) vPassword: String,
        @Field(AppConstant.app_version) vAppVersion: String,
        @Field(AppConstant.device_type) eDeviceType: String,
        @Field(AppConstant.device_token) vDeviceToken: String,
        @Field(AppConstant.device_name) vDeviceName: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): RegisterUserRS


    @Multipart
    @POST(ApiConstant.ApiRegisterUser)
    suspend fun registerUser(
        @Part(AppConstant.language) language: RequestBody,
        @Part(AppConstant.user_type) user_type: RequestBody,
        @Part(AppConstant.fullname) fullname: RequestBody,
        @Part(AppConstant.phone) phone: RequestBody,
        @Part(AppConstant.email) email: RequestBody,
        @Part(AppConstant.password) password: RequestBody,
        @Part(AppConstant.timezone_detail) timezone_detail: RequestBody,
        @Part(AppConstant.app_version) app_version: RequestBody,
        @Part(AppConstant.device_type) device_type: RequestBody,
        @Part(AppConstant.device_token) device_token: RequestBody,
        @Part(AppConstant.device_name) device_name: RequestBody,
        @Part(AppConstant.latitude) latitude: RequestBody,
        @Part(AppConstant.longitude) longitude: RequestBody,
        @Part profile_pic: MultipartBody.Part
    ): RegisterUserRS

    @FormUrlEncoded
    @POST(ApiConstant.ApiOtpVerification)
    suspend fun verifyOtp(
        @Field(AppConstant.authKey) vAuthKey: String,
        @Field(AppConstant.otp) otp: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): RegisterUserRS


    @FormUrlEncoded
    @POST(ApiConstant.ApiResendOtp)
    suspend fun resendOtp(
        @Field(AppConstant.authKey) vAuthKey: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): RegisterUserRS

    @FormUrlEncoded
    @POST(ApiConstant.ApiAutoLogin)
    suspend fun checkAutoLogin(
        @Field(AppConstant.authKey) vAuthKey: String,
        @Field(AppConstant.app_version) app_version: String,
        @Field(AppConstant.device_token) vDeviceToken: String,
        @Field(AppConstant.vTimezone) vTimezone: String,
        @Field(AppConstant.vDeviceUniqueId) vDeviceUniqueId: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): RegisterUserRS

    @FormUrlEncoded
    @POST(ApiConstant.ApiForgotPassword)
    suspend fun forgotPassword(
        @Field(AppConstant.email) vEmail: String? = "",
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): BaseResponse


    //    @FormUrlEncoded
    //    @POST(ApiConstant.ApiGuestLogin)
    //    suspend fun guestLogin(
    //        @Field(AppConstant.vDeviceUniqueId) vDeviceUniqueId: String,
    //        @Field(AppConstant.device_name) vDeviceName: String,
    //        @Field(AppConstant.device_type) eDeviceType: String,
    //        @Field(AppConstant.device_token) vDeviceToken: String,
    //        @Field(AppConstant.app_version) vAppVersion: String,
    //        @Field(AppConstant.latitude) vLat: String,
    //        @Field(AppConstant.longitude) vLong: String,
    //        @Field(AppConstant.vTimezone) vTimezone: String,
    //        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    //    ): GuestLoginRS

    @FormUrlEncoded
    @POST(ApiConstant.ApiResetPassword)
    suspend fun resetPassword(
        @Field(AppConstant.email) email: String,
        @Field(AppConstant.password) password: String,
        @Field(AppConstant.otp) otp: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): ResetPasswordRS

    @FormUrlEncoded
    @POST(ApiConstant.ApiChangePassword)
    suspend fun changePassword(
        @Field(AppConstant.authKey) vAuthKey: String,
        @Field(AppConstant.oldPassword) oldPassword: String,
        @Field(AppConstant.newPassword) newPassword: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): BaseResponse

    @Multipart
    @POST(ApiConstant.ApiUpdateProfile)
    suspend fun updateProfile(
        @Part(AppConstant.authKey) vAuthKey: RequestBody,
        @Part(AppConstant.language) language: RequestBody,
        @Part(AppConstant.fullname) fullname: RequestBody,
        @Part profile_pic: MultipartBody.Part? = null
    ): RegisterUserRS

}