package com.demo.android.presentation.loginsignup.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.demo.android.R
import com.demo.android.databinding.ActivityForgotPasswordBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.loginsignup.LoginSignupViewModel
import com.demo.android.presentation.utility.*
import org.koin.android.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : BaseActivity() {

    private val loginSignupViewModel: LoginSignupViewModel by viewModel()

    override fun getBaseViewModel() = loginSignupViewModel

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        attachObserver()
        setupClickListeners()
    }

    private fun attachObserver() {
        loginSignupViewModel.forgotPasswordRSLiveData.observe(this, Observer {
            it?.apply {
                hideProgress()
                if (this.isSuccess()) {
                    startActivityCustom(
                        IntentHelper.getRestPasswordScreenIntent(this@ForgotPasswordActivity).also {
                            val bundle = Bundle()
                            bundle.putString(AppConstant.email, binding.edtEmail.text.toString())
                            it.putExtras(bundle)
                        }
                    )
                    toastSuccess(this.message.orEmpty())
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

        binding.btnSubmit.setOnClickListener {
            performForgotPassword()
        }
    }

    private fun performForgotPassword() {
        val email = binding.edtEmail.text.toString()
        if (email.isEmpty()) {
            toastError(getString(R.string.validation_email))
            return
        }
        if (!isValidEmail(email)) {
            toastError(getString(R.string.error_email))
            return
        }
        showProgress()
        loginSignupViewModel.forgotPassword(email)
    }

}