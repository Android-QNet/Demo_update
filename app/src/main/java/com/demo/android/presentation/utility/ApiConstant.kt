package com.demo.android.presentation.utility


class ApiConstant {
    companion object {


        const val ApiBeforeAuth = "api"
        const val ApiAfterAuth = "api"

        //Before Auth
        const val ApiRegisterUser = "$ApiBeforeAuth/signup"
        const val ApiLogin = "$ApiBeforeAuth/login"
        const val ApiForgotPassword = "$ApiBeforeAuth/forgot_password"
        const val ApiResetPassword = "$ApiBeforeAuth/reset_password"
        const val ApiGetPages = "$ApiBeforeAuth/get_pages"
        const val ApiChangePassword = "$ApiAfterAuth/change_password"
        const val ApiFaqsList = "$ApiBeforeAuth/faqs"
        const val ApiContactUs = "$ApiAfterAuth/contact"
        const val ApiGuestLogin = "$ApiBeforeAuth/guestLogin"

        //After Auth
        const val ApiAutoLogin = "$ApiAfterAuth/autoLogin"
        const val ApiOtpVerification = "$ApiAfterAuth/verify_otp"
        const val ApiResendOtp = "$ApiAfterAuth/resend_otp"
        const val ApiUpdateProfile = "$ApiAfterAuth/update_personal_profile"
        const val ApiLogout = "$ApiAfterAuth/logout"

    }
}