package com.demo.android.presentation.loginsignup.activities

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.demo.android.R
import com.demo.android.databinding.ActivityChangePasswordBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.loginsignup.LoginSignupViewModel
import com.demo.android.presentation.utility.isPasswordAndConfirmPasswordMatch
import com.demo.android.presentation.utility.isValidPassword
import com.demo.android.presentation.utility.toastError
import com.demo.android.presentation.utility.toastSuccess
import org.koin.android.viewmodel.ext.android.viewModel

class ChangePasswordActivity : BaseActivity() {

    private val loginSignupViewModel: LoginSignupViewModel by viewModel()

    override fun getBaseViewModel() = loginSignupViewModel

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {

        setupToolbar(
            binding.llToolbarMain.toolbar, getString(R.string.change_password), true, Color.WHITE,
            ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_arrow_back_white
        )

        attachObserver()
        setupClickListeners()
    }

    private fun attachObserver() {
        loginSignupViewModel.changePasswordRSLiveData.observe(this, Observer {
            it?.apply {
                hideProgress()
                if (this.isSuccess()) {
                    toastSuccess(this.message.orEmpty())
                    finish()
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

        binding.btnUpdate.setOnClickListener {
            performChangePassword()
        }
    }

    private fun performChangePassword() {
        val currentpassword = binding.edtCurrentPassword.text.toString()
        val newPassword = binding.edtNewPassword.text.toString()
        val confirmnewPassword = binding.edtConfirmPassword.text.toString()

        if (currentpassword.isEmpty()) {
            toastError(getString(R.string.validation_current_password))
            return
        }
        if (!isValidPassword(newPassword)) {
            toastError(getString(R.string.error_password))
            return
        }
        if (!isValidPassword(confirmnewPassword)) {
            toastError(getString(R.string.error_password))
            return
        }
        if (!isPasswordAndConfirmPasswordMatch(newPassword, confirmnewPassword)) {
            toastError(getString(R.string.error_new_password_conflict))
            return
        }
        showProgress()
        loginSignupViewModel.changePassword(
            currentpassword,
            newPassword
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}