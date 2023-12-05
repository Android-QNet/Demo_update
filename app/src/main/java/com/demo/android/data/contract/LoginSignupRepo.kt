package com.demo.android.data.contract

import com.demo.android.data.Either
import com.demo.android.data.models.*
import com.demo.android.domain.exceptions.MyException


interface LoginSignupRepo {
    suspend fun login(loginPRQ: LoginPRQ): Either<MyException, RegisterUserRS>
    suspend fun registerUser(registerUserPRQ: RegisterUserPRQ): Either<MyException, RegisterUserRS>
    suspend fun updateProfile(updateProfilePRQ: UpdateProfilePRQ): Either<MyException, RegisterUserRS>

    suspend fun verifyOtp(verificationPRQ: OtpVerificationPRQ): Either<MyException, RegisterUserRS>
    suspend fun resendOtp(resendOtpPRQ: ResendOtpPRQ): Either<MyException, BaseResponse>
    suspend fun checkAutoLogin(autoLoginPRQ: AutoLoginPRQ): Either<MyException, RegisterUserRS>
    suspend fun forgotPassword(forgotPasswordPRQ: ForgotPasswordPRQ): Either<MyException, BaseResponse>
    suspend fun resetPassword(resetPasswordPRQ: ResetPasswordPRQ): Either<MyException, ResetPasswordRS>
    suspend fun changePassword(changePasswordPRQ: ChangePasswordPRQ): Either<MyException, BaseResponse>

}