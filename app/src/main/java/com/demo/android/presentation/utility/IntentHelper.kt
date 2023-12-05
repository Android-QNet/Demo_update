package com.demo.android.presentation.utility



import android.content.Context
import android.content.Intent
import com.demo.android.presentation.home.HomeActivity
import com.demo.android.presentation.home.activities.CMSActivity
import com.demo.android.presentation.loginsignup.*
import com.demo.android.presentation.loginsignup.activities.ChangePasswordActivity
import com.demo.android.presentation.loginsignup.activities.ForgotPasswordActivity
import com.demo.android.presentation.loginsignup.activities.OtpVerificationActivity
import com.demo.android.presentation.loginsignup.activities.ResetPasswordActivity

class IntentHelper {

    companion object {

        fun getHomeScreenIntent(context: Context, isClearFlag: Boolean? = false): Intent {
            return Intent(context, HomeActivity::class.java).also {
                if (isClearFlag != null) {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }

        fun getLoginScreenIntent(context: Context, isClearFlag: Boolean? = false): Intent {
            return Intent(context, LoginActivity::class.java).also {
                if (isClearFlag != null && isClearFlag) {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }

        fun getSignupScreenIntent(context: Context): Intent {
            return Intent(context, SignupActivity::class.java)
        }

        fun getOtpScreenIntent(context: Context): Intent {
            return Intent(context, OtpVerificationActivity::class.java)
        }

        fun getForgotPasswordScreenIntent(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }

        fun getRestPasswordScreenIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }

        fun getChangePasswordScreenIntent(context: Context): Intent {
            return Intent(context, ChangePasswordActivity::class.java)
        }

        fun getCMSScreenIntent(context: Context): Intent {
            return Intent(context, CMSActivity::class.java)
        }

    }
}