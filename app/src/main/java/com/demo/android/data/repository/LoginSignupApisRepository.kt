package com.demo.android.data.repository


import com.demo.android.data.BaseRepository
import com.demo.android.data.Either
import com.demo.android.data.contract.LoginSignupRepo
import com.demo.android.data.models.*
import com.demo.android.domain.exceptions.MyException
import com.demo.android.domain.network.LoginSignupApiService
import com.demo.android.presentation.utility.AppConstant
import com.demo.android.presentation.utility.PrefKeys
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class LoginSignupApisRepository constructor(
    private val loginSignupApiService: LoginSignupApiService
) : LoginSignupRepo, BaseRepository() {

    override suspend fun login(loginPRQ: LoginPRQ): Either<MyException, RegisterUserRS> {
        return either {
            loginSignupApiService.login(
                loginPRQ.email.orEmpty(),
                loginPRQ.password.orEmpty(),
                loginPRQ.app_version.orEmpty(),
                loginPRQ.device_type.orEmpty(),
                loginPRQ.device_token.orEmpty(),
                loginPRQ.device_name.orEmpty()
            )
        }
    }

    override suspend fun registerUser(registerUserPRQ: RegisterUserPRQ): Either<MyException, RegisterUserRS> {

        val mediaType = MediaType.parse("multipart/form-data")

        val languageBody = RequestBody.create(mediaType, registerUserPRQ.language.orEmpty())
        val userTypeBody = RequestBody.create(mediaType, registerUserPRQ.user_type.orEmpty())
        val fullNameBody = RequestBody.create(mediaType, registerUserPRQ.fullname.orEmpty())
        val emailBody = RequestBody.create(mediaType, registerUserPRQ.email.orEmpty())
        val mobileBody = RequestBody.create(mediaType, registerUserPRQ.phone.orEmpty())
        val passwordBody = RequestBody.create(mediaType, registerUserPRQ.password.orEmpty())
        val timezoneBody = RequestBody.create(mediaType, registerUserPRQ.timezone_detail.orEmpty())
        val appVersionBody = RequestBody.create(mediaType, registerUserPRQ.app_version.orEmpty())
        val deviceTypeBody = RequestBody.create(mediaType, registerUserPRQ.device_type.orEmpty())
        val deviceTokenBody = RequestBody.create(mediaType, registerUserPRQ.device_token.orEmpty())
        val deviceNameBody = RequestBody.create(mediaType, registerUserPRQ.device_name.orEmpty())
        val latitudeBody = RequestBody.create(mediaType, registerUserPRQ.latitude.orEmpty())
        val longitudeBody = RequestBody.create(mediaType, registerUserPRQ.longitude.orEmpty())

        val profileImage = File(registerUserPRQ.profile_pic.orEmpty())
        val profilePic = MultipartBody.Part.createFormData(
            AppConstant.profile_pic,
            profileImage.name,
            RequestBody.create(MediaType.parse("image/*"), profileImage)
        )
        return either {
            loginSignupApiService.registerUser(
                languageBody,
                userTypeBody,
                fullNameBody,
                mobileBody,
                emailBody,
                passwordBody,
                timezoneBody,
                appVersionBody,
                deviceTypeBody,
                deviceTokenBody,
                deviceNameBody,
                latitudeBody,
                longitudeBody,
                profilePic
            )
        }
    }

    override suspend fun updateProfile(updateProfilePRQ: UpdateProfilePRQ): Either<MyException, RegisterUserRS> {

        val mediaType = MediaType.parse("multipart/form-data")
        val authKeyBody = RequestBody.create(mediaType, PrefKeys.getAuthKey())
        val fullNameBody = RequestBody.create(mediaType, updateProfilePRQ.fullname.orEmpty())
        val languageBody = RequestBody.create(mediaType, updateProfilePRQ.language.orEmpty())

        var profilePic: MultipartBody.Part? = null
        if (updateProfilePRQ.profile_pic?.isNotEmpty() == true) {
            val profileImage = File(updateProfilePRQ.profile_pic.orEmpty())
            profilePic = MultipartBody.Part.createFormData(
                AppConstant.profile_pic,
                profileImage.name,
                RequestBody.create(MediaType.parse("image/*"), profileImage)
            )
        }

        return either {
            loginSignupApiService.updateProfile(
                authKeyBody,
                languageBody,
                fullNameBody,
                profilePic
            )
        }
    }

    override suspend fun verifyOtp(verificationPRQ: OtpVerificationPRQ): Either<MyException, RegisterUserRS> {
        return either {
            loginSignupApiService.verifyOtp(
                verificationPRQ.authKey,
                verificationPRQ.iPhoneVerificationCode
            )
        }
    }

    override suspend fun resendOtp(resendOtpPRQ: ResendOtpPRQ): Either<MyException, BaseResponse> {
        return either {
            loginSignupApiService.resendOtp(
                resendOtpPRQ.authKey
            )
        }
    }

    override suspend fun checkAutoLogin(autoLoginPRQ: AutoLoginPRQ): Either<MyException, RegisterUserRS> {
        return either {
            loginSignupApiService.checkAutoLogin(
                autoLoginPRQ.vAuthKey,
                autoLoginPRQ.appVersion,
                autoLoginPRQ.vDeviceToken,
                autoLoginPRQ.vTimezone,
                autoLoginPRQ.vDeviceUniqueId
            )
        }
    }

    override suspend fun forgotPassword(forgotPasswordPRQ: ForgotPasswordPRQ): Either<MyException, BaseResponse> {
        return either {
            loginSignupApiService.forgotPassword(
                forgotPasswordPRQ.email.orEmpty()
            )
        }
    }

    override suspend fun resetPassword(resetPasswordPRQ: ResetPasswordPRQ): Either<MyException, ResetPasswordRS> {
        return either {
            loginSignupApiService.resetPassword(
                resetPasswordPRQ.email,
                resetPasswordPRQ.password,
                resetPasswordPRQ.otp
            )
        }
    }

    override suspend fun changePassword(changePasswordPRQ: ChangePasswordPRQ): Either<MyException, BaseResponse> {
        return either {
            loginSignupApiService.changePassword(
                changePasswordPRQ.authKey,
                changePasswordPRQ.oldPassword,
                changePasswordPRQ.newPassword
            )
        }
    }

    //    override suspend fun checkSocialLogin(socialLoginPRQ: SocialLoginPRQ): Either<MyException, RegisterUserRS> {
    //        return either {
    //            loginSignupApiService.checkSocialLogin(
    //                socialLoginPRQ.vSocialId.orEmpty(),
    //                socialLoginPRQ.vDeviceToken.orEmpty(),
    //                socialLoginPRQ.vDeviceUniqueId.orEmpty(),
    //                socialLoginPRQ.vTimezone.orEmpty(),
    //                socialLoginPRQ.eLoginType.orEmpty(),
    //                socialLoginPRQ.fAppVersion.orEmpty(),
    //                socialLoginPRQ.vDeviceType.orEmpty(),
    //                socialLoginPRQ.vDeviceName.orEmpty()
    //            )
    //        }
    //    }


}