package com.demo.android.presentation.loginsignup



import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.demo.android.BuildConfig
import com.demo.android.data.contract.LoginSignupRepo
import com.demo.android.data.models.*
import com.demo.android.presentation.core.BaseViewModel
import com.demo.android.presentation.utility.AppConstant
import com.demo.android.presentation.utility.PrefKeys
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.launch

class LoginSignupViewModel(private val loginSignupRepo: LoginSignupRepo) : BaseViewModel() {

    val loginUserRSLiveData: MutableLiveData<RegisterUserRS> = MutableLiveData()
    val registerUserRSLiveData: MutableLiveData<RegisterUserRS> = MutableLiveData()
    val optVerificationRSLiveData: MutableLiveData<RegisterUserRS> = MutableLiveData()
    val resendOtpRSLiveData: MutableLiveData<BaseResponse> = MutableLiveData()
    val autoLoginRSLiveData: MutableLiveData<RegisterUserRS> = MutableLiveData()
    val forgotPasswordRSLiveData: MutableLiveData<BaseResponse> = MutableLiveData()
    val resetPasswordRSLiveData: MutableLiveData<ResetPasswordRS> = MutableLiveData()
    val changePasswordRSLiveData: MutableLiveData<BaseResponse> = MutableLiveData()


    fun login(email: String, password: String) {
        launch {
            val loginPRQ = LoginPRQ(
                email,
                password,
                BuildConfig.VERSION_NAME,
                AppConstant.DeviceTypeAndroid,
                Prefs.getString(PrefKeys.PushTokenKey, ""),
                Build.DEVICE
            )
            postValue(loginSignupRepo.login(loginPRQ), loginUserRSLiveData)
        }
    }

    fun registerUser(registerUserPRQ: RegisterUserPRQ) {
        launch {
            postValue(loginSignupRepo.registerUser(registerUserPRQ), registerUserRSLiveData)
        }
    }

    fun updateProfile(updateProfilePRQ: UpdateProfilePRQ) {
        launch {
            postValue(loginSignupRepo.updateProfile(updateProfilePRQ), registerUserRSLiveData)
        }
    }

    fun verifyOtp(verificationPRQ: OtpVerificationPRQ) {
        launch {
            postValue(loginSignupRepo.verifyOtp(verificationPRQ), optVerificationRSLiveData)
        }
    }

    fun resendOtp() {
        launch {
            postValue(
                loginSignupRepo.resendOtp(ResendOtpPRQ(PrefKeys.getAuthKey())),
                resendOtpRSLiveData
            )
        }
    }

    fun checkAutoLogin(timeZone: String) {
        launch {
            val autoLoginPRQ = AutoLoginPRQ(
                PrefKeys.getAuthKey(),
                BuildConfig.VERSION_NAME,
                Prefs.getString(PrefKeys.PushTokenKey, ""),
                timeZone,
                PrefKeys.getAndroidId()
            )
            postValue(loginSignupRepo.checkAutoLogin(autoLoginPRQ), autoLoginRSLiveData)
        }
    }

    fun forgotPassword(email: String) {
        val forgotPasswordPRQ = ForgotPasswordPRQ(
            email
        )
        launch {
            postValue(loginSignupRepo.forgotPassword(forgotPasswordPRQ), forgotPasswordRSLiveData)
        }
    }

    fun resetPassword(email: String, password: String, otp: String) {
        val resetPasswordPRQ = ResetPasswordPRQ(
            email,
            password,
            otp
        )
        launch {
            postValue(loginSignupRepo.resetPassword(resetPasswordPRQ), resetPasswordRSLiveData)
        }
    }

    fun changePassword(old_password: String, new_password: String) {
        val resetPasswordPRQ = ChangePasswordPRQ(
            PrefKeys.getAuthKey(),
            old_password,
            new_password
        )
        launch {
            postValue(loginSignupRepo.changePassword(resetPasswordPRQ), changePasswordRSLiveData)
        }
    }



}