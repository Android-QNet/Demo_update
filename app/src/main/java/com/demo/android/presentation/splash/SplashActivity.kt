package com.demo.android.presentation.splash


import android.os.Bundle
//import com.capermint.android.BuildConfig
import com.demo.android.databinding.ActivitySplashBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.utility.*
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun getBaseViewModel() = splashViewModel

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {

        Logger.d("Firebase push : " + Prefs.getString(PrefKeys.PushTokenKey, ""))
        Logger.d("Device Android ID : " + getAndroidID())
        Prefs.putString(PrefKeys.AndroidId, getAndroidID())
       /* if (BuildConfig.DEBUG) {
            binding.tvAppVersion.visible()
        } else {
            binding.tvAppVersion.gone()
        }*/

        launch {
            delay(AppConstant.TIMEOUT)
            startActivityCustom(IntentHelper.getLoginScreenIntent(this@SplashActivity))
        }
    }
}
