package com.demo.android.presentation.loginsignup



import android.graphics.Paint
import android.os.Bundle
import androidx.lifecycle.Observer
import com.demo.android.R
import com.demo.android.databinding.ActivityLoginBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.utility.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val loginSignupViewModel: LoginSignupViewModel by viewModel()

    override fun getBaseViewModel() = loginSignupViewModel

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        attachObserver()

        binding.tvTermsAndCondition.paintFlags =
            binding.tvTermsAndCondition.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG
        binding.tvPrivacyPolicy.paintFlags = binding.tvPrivacyPolicy.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG

        setupClickListeners()
    }

    private fun attachObserver() {
        loginSignupViewModel.loginUserRSLiveData.observe(this, Observer {
            it?.apply {
                hideProgress()
                if (this.isSuccess()) {
                    this.user?.apply {
                        PrefKeys.setUser(this)
                    }
                    toastSuccess(this.message.orEmpty())
                    if (this.isSuccess() && this.user?.isPhoneVerified() == true) {
                        startActivityCustom(
                            IntentHelper.getHomeScreenIntent(this@LoginActivity, true)
                        )
                    } else {
                        startActivityCustom(IntentHelper.getOtpScreenIntent(this@LoginActivity))
                    }
                } else {
                    toastError(this.message.orEmpty())
                }
            }
        })
    }

    private fun setupClickListeners() {
        binding.tvSignup.setOnClickListener {
            startActivityCustom(
                IntentHelper.getSignupScreenIntent(this)
            )
        }
        binding.btnContinue.setOnClickListener {
            performLogin()
        }
        binding.tvForgot.setOnClickListener {
            startActivityCustom(
                IntentHelper.getForgotPasswordScreenIntent(this)
            )
        }
        binding.llSkip.setOnClickListener {
            startActivityCustom(
                IntentHelper.getHomeScreenIntent(this, true)
            )
        }
        binding.tvTermsAndCondition.setOnClickListener {
            startActivityCustom(
                IntentHelper.getCMSScreenIntent(this).also {
                    val bundle = Bundle()
                    bundle.putString(AppConstant.cmsType, AppConstant.termsAndConditions)
                    it.putExtras(bundle)
                }
            )
        }
        binding.tvPrivacyPolicy.setOnClickListener {
            startActivityCustom(
                IntentHelper.getCMSScreenIntent(this).also {
                    val bundle = Bundle()
                    bundle.putString(AppConstant.cmsType, AppConstant.privacyPolicy)
                    it.putExtras(bundle)
                }
            )
        }
    }

    private fun performLogin() {
        val email = binding.edtEmail.text.toString()
        val pass = binding.edtPassword.text.toString()
        if (email.isEmpty()) {
            toastError(getString(R.string.error_email))
            return
        }
        if (!isValidEmail(email)) {
            toastError(getString(R.string.error_email))
            return
        }
        if (pass.isEmpty()) {
            toastError(getString(R.string.validation_password))
            return
        }
        if(!isValidPassword(pass)){
            toastError(getString(R.string.error_password))
            return
        }
        showProgress()
        loginSignupViewModel.login(email, pass)
    }
}
