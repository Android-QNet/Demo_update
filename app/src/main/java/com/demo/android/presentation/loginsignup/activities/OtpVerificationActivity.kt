package com.demo.android.presentation.loginsignup.activities

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.Observer
import com.demo.android.R
import com.demo.android.data.models.OtpVerificationPRQ
import com.demo.android.databinding.ActivityOtpVerificationBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.loginsignup.LoginSignupViewModel
import com.demo.android.presentation.utility.*
import org.koin.android.viewmodel.ext.android.viewModel

class OtpVerificationActivity : BaseActivity() {

    private val loginSignupViewModel: LoginSignupViewModel by viewModel()

    override fun getBaseViewModel() = loginSignupViewModel

    private lateinit var binding: ActivityOtpVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {

        attachObserver()
        setupClickListeners()
        startTimer()

    }

    private fun attachObserver() {
        loginSignupViewModel.optVerificationRSLiveData.observe(this, Observer {
            it?.apply {
                hideProgress()
                showDialog(getString(R.string.app_name),
                    this.message.orEmpty(),
                    getString(R.string.ok), DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                        if (this.isSuccess()) {
                            this.user?.apply {
                                PrefKeys.setUser(this)
                            }
                            startActivityCustom(IntentHelper.getHomeScreenIntent(this@OtpVerificationActivity))
                        }
                    })
            }
        })

        loginSignupViewModel.resendOtpRSLiveData.observe(this, Observer {
            it?.apply {
                hideProgress()
                showDialog(getString(R.string.app_name),
                    this.message.orEmpty(),
                    getString(R.string.ok), DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                startTimer()
            }
        })
    }


    private fun startTimer() {
        object : CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvSeconds.visible()
                binding.tvResendCode.invisible()
                var second = ""
                if ((millisUntilFinished / 1000) < 10) {
                    second = "0" + (millisUntilFinished / 1000)
                } else {
                    second = (millisUntilFinished / 1000)?.toString()
                }
                binding.tvSeconds.text = "00:" + second + ""
            }

            override fun onFinish() {
                binding.tvSeconds.invisible()
                binding.tvResendCode.visible()
            }
        }.start()
    }

    private fun setupClickListeners() {
        binding.btnVerify.setOnClickListener {
            verifyOtp()
        }

        binding.tvResendCode.setOnClickListener {
            resendOtp()
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun verifyOtp() {
        val otp = binding.otpView.text.toString()
        if (otp.length < this.resources.getInteger(R.integer.otp_length)) {
            showDialog(getString(R.string.app_name),
                getString(R.string.error_otp),
                getString(R.string.ok), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
            return
        }
        showProgress()
        val otpVerificationPRQ = OtpVerificationPRQ(
            user?.authKey.orEmpty(),
            otp
        )
        loginSignupViewModel.verifyOtp(otpVerificationPRQ)
    }

    private fun resendOtp() {
        showProgress()
        loginSignupViewModel.resendOtp()
    }

}