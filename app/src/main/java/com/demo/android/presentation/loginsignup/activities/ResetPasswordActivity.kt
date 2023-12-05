package com.demo.android.presentation.loginsignup.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.lifecycle.Observer
import com.demo.android.R
import com.demo.android.databinding.ActivityResetPasswordBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.loginsignup.LoginSignupViewModel
import com.demo.android.presentation.utility.*
import org.koin.android.viewmodel.ext.android.viewModel

class ResetPasswordActivity : BaseActivity() {

    private val loginSignupViewModel: LoginSignupViewModel by viewModel()

    override fun getBaseViewModel() = loginSignupViewModel

    private lateinit var binding: ActivityResetPasswordBinding

    var email: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        attachObserver()
        setupClickListeners()
        if (intent.extras?.containsKey(AppConstant.email) == true) {
            email = intent?.extras?.getString(AppConstant.email)
        }
    }

    private fun attachObserver() {
        loginSignupViewModel.resetPasswordRSLiveData.observe(this, Observer {
            it?.apply {
                hideProgress()
                if (this.isSuccess()) {
                    toastSuccess(this.message.orEmpty())
                    startActivityCustom(
                        IntentHelper.getLoginScreenIntent(this@ResetPasswordActivity, true)
                    )
                } else {
                    toastError(this.message.orEmpty())
                }
            }
        })
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnReset.setOnClickListener {
            performResetPassword()
        }
    }

    private fun performResetPassword() {
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()
        val otp = binding.otpView.text.toString()

        if (password.isEmpty()) {
            toastError(getString(R.string.validation_password))
            return
        }
        if (confirmPassword.isEmpty()) {
            toastError(getString(R.string.validation_password))
            return
        }
        if (password.length < resources.getInteger(R.integer.min_length_password)
            || password.length > resources.getInteger(R.integer.max_length_password)
        ) {
            toastError(getString(R.string.error_password))
            return
        }
        if (!password.contentEquals(confirmPassword)) {
            toastError(getString(R.string.error_password_conflict))
            return
        }
        if (otp.length < this.resources.getInteger(R.integer.otp_length)) {
            showDialog(getString(R.string.app_name),
                getString(R.string.error_otp),
                getString(R.string.ok), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
            return
        }

        showProgress()
        loginSignupViewModel.resetPassword(
            email.orEmpty(),
            password,
            otp
        )
    }
}