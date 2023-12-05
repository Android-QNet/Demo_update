package com.demo.android.presentation.loginsignup

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.demo.android.BuildConfig
import com.demo.android.R
import com.demo.android.data.models.RegisterUserPRQ
import com.demo.android.databinding.ActivitySignupBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.core.location.MyLocationListener
import com.demo.android.presentation.core.location.MyLocationManager
import com.demo.android.presentation.utility.*
import com.thanosfisherman.mayi.MayI
import com.thanosfisherman.mayi.PermissionBean
import com.thanosfisherman.mayi.PermissionToken
import com.theartofdev.edmodo.cropper.CropImage
import gun0912.tedbottompicker.TedBottomPicker
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class SignupActivity : BaseActivity() {

    private val loginSignupViewModel: LoginSignupViewModel by viewModel()

    override fun getBaseViewModel() = loginSignupViewModel

    private lateinit var binding: ActivitySignupBinding

    private var locationManager: MyLocationManager? = null

    private var profileImageUri: Uri? = null

    private val PICK_PDF_FILE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        attachObserver()

        setupClickListeners()

        checkGpsStatus()
    }

    private fun attachObserver() {
        loginSignupViewModel.registerUserRSLiveData.observe(this, Observer {
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
                            //otp
                            startActivityCustom(IntentHelper.getOtpScreenIntent(this@SignupActivity).also {
                                val bundle = Bundle()
                                bundle.putString(AppConstant.phone, binding.edtMobileNumber.text.toString().trim())
                                it.putExtras(bundle)
                            })
                            finish()
                        }
                    })
            }
        })
    }

    private fun setupClickListeners() {
        binding.llClose.setOnClickListener {
            onBackPressed()
        }

        binding.llProfilePhoto.setOnClickListener {
            checkForPermission()
        }

        binding.btnSingup.setOnClickListener {
            performSignup()
        }

    }


    private fun performSignup() {
        val fullName = binding.edtFullName.text.toString()
        val email = binding.edtEmail.text.toString()
        val mobile = binding.edtMobileNumber.text.toString()
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (fullName.isEmpty() || fullName.length < this.resources.getInteger(R.integer.min_length)) {
            toastError(getString(R.string.validation_fullname))
            return
        }
        if (email.isEmpty()) {
            toastError(getString(R.string.validation_email))
            return
        }
        if (!isValidEmail(email)) {
            toastError(getString(R.string.error_email))
            return
        }
        if (mobile.isEmpty()) {
            toastError(getString(R.string.validation_mobile))
            return
        }
        if (!isValidPhone(mobile)) {
            toastError(getString(R.string.error_mobile))
            return
        }
        if (password.isEmpty()) {
            toastError(getString(R.string.validation_password))
            return
        }
        if (confirmPassword.isEmpty()) {
            toastError(getString(R.string.validation_confirm_password))
            return
        }
        if (!isValidPassword(password)) {
            toastError(getString(R.string.error_password))
            return
        }
        if (!isPasswordAndConfirmPasswordMatch(password, confirmPassword)) {
            toastError(getString(R.string.error_password_conflict))
            return
        }
        if (profileImageUri == null) {
            toastError(getString(R.string.validation_profile_image))
            return
        }

        val registerUserPRQ = RegisterUserPRQ(
            PrefKeys.getLanguageSelectedCapital(),
            "",
            fullName,
            mobile,
            email,
            password,
            getDeviceTimeZone(),
            BuildConfig.VERSION_NAME,
            AppConstant.DeviceTypeAndroid,
            PrefKeys.getFirebasePushToken(),
            Build.BRAND,
            PrefKeys.getLatitude(),
            PrefKeys.getLongitude(),
            profileImageUri?.path.orEmpty()
        )
        showProgress()
        loginSignupViewModel.registerUser(registerUserPRQ)
    }


    //#region - storage permission

    private fun checkForPermission() {
        MayI.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .onRationale(this::permissionRationaleMulti)
            .onResult(this::permissionResultMulti)
            .onErrorListener(this::inCaseOfError)
            .check()
    }

    private fun permissionRationaleMulti(
        permissions: Array<PermissionBean>,
        token: PermissionToken
    ) {
        showDialog(getString(R.string.app_name),
            getString(R.string.permission_storage),
            getString(R.string.ok),
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                token.continuePermissionRequest()
            })
    }

    private fun inCaseOfError(e: Exception) {
        showDialog(getString(R.string.app_name),
            "Error for permission : " + e.message,
            getString(R.string.ok),
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
    }

    private fun permissionResultMulti(
        permissions: Array<PermissionBean>
    ) {
        val isAllPermanentlyDenied = permissions.all {
            it.isPermanentlyDenied
        }
        if(isAllPermanentlyDenied){
            showDialog(
                getString(R.string.app_name),
                getString(R.string.permission_always_denied),
                getString(R.string.settings), { dialog, which ->
                    dialog.dismiss()
                    openPermissionSettings()
                }, getString(R.string.cancel), { dialog, which ->
                    dialog.dismiss()
                    finish()
                }
            )
            return
        }
        val isAllPermissionGranted = permissions.all {
            it.isGranted
        }
        if (isAllPermissionGranted) {
            openImagePicker()
        } else {
            checkForLocationPermission()
        }
    }

    private fun openImagePicker() {
        showImagePicker(TedBottomPicker.OnImageSelectedListener { uri ->
            // profileImageUri = uri

            CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .start(this)

            //            profileImageUri?.apply {
            //                binding.ivProfile.loadImageRound(this)
            //            }
        })
    }

    //endregion

    //#region - location permission

    private fun checkGpsStatus() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            checkForLocationPermission()
        } else {
            showGPSDisabledAlertToUser()
        }
    }

    private fun showGPSDisabledAlertToUser() {
        showDialog(getString(R.string.app_name),
            getString(R.string.gps_permission_msg),
            getString(R.string.yes), { dialog, which ->
                dialog.dismiss()

                val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(callGPSSettingIntent)

            },
            getString(R.string.cancel), { dialog, which ->
                dialog.dismiss()
            })
    }

    private fun checkForLocationPermission() {
        MayI.withActivity(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .onRationale(this::permissionRationaleMultiLocation)
            .onResult(this::permissionResultMultiLocation)
            .onErrorListener(this::inCaseOfErrorLocation)
            .check()
    }

    private fun permissionRationaleMultiLocation(
        permissions: Array<PermissionBean>,
        token: PermissionToken
    ) {
        showDialog(getString(R.string.app_name),
            getString(R.string.permission_location),
            getString(R.string.ok),
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                token.continuePermissionRequest()
            })
    }

    private fun inCaseOfErrorLocation(e: Exception) {
        showDialog(getString(R.string.app_name),
            "Error for permission : " + e.message,
            getString(R.string.ok),
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
    }

    private fun permissionResultMultiLocation(
        permissions: Array<PermissionBean>
    ) {
        val isAllPermanentlyDenied = permissions.all {
            it.isPermanentlyDenied
        }
        if(isAllPermanentlyDenied){
            showDialog(
                getString(R.string.app_name),
                getString(R.string.permission_always_denied),
                getString(R.string.settings), { dialog, which ->
                    dialog.dismiss()
                    openPermissionSettings()
                }, getString(R.string.cancel), { dialog, which ->
                    dialog.dismiss()
                    finish()
                }
            )
            return
        }
        val isAllPermissionGranted = permissions.all {
            it.isGranted
        }
        if (isAllPermissionGranted) {
            getCurrentLocation()
        }
    }

    //location for skip login

    private fun getCurrentLocation() {
        toastSuccess(getString(R.string.str_location_fetching))
        locationManager = MyLocationManager(this)
        locationManager?.myLocationManager = locationListener
        locationManager?.startLocation()
    }

    private var locationListener = object : MyLocationListener {
        override fun onLocationReceived(location: Location) {
            val strLocation = String.format("%f,%f", location.latitude, location.longitude)
            Logger.d("Location received : $strLocation")
            locationManager?.stopLocation()
        }

        override fun onLocationError() {
            showDialog(
                getString(R.string.app_name),
                getString(R.string.location_error),
                getString(R.string.ok), { dialog, which ->
                    dialog.dismiss()
                }, getString(R.string.cancel), { dialog, which ->
                    dialog.dismiss()
                })
        }
    }

    //endregion

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            var businessLicenceImageUri: Uri? = null
            data?.data?.also { uri ->
                val dir = File(this.filesDir, "doc")
                dir.mkdirs()
                val file = File(dir, "document.pdf")
                file.createNewFile()
                val fout = FileOutputStream(file)
                try {
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        fout.use { output ->
                            inputStream.copyTo(output)
                            output.flush()
                        }
                    }
                    businessLicenceImageUri = FileProvider.getUriForFile(this, packageName + ".provider", file)
                } catch (e: Exception) {
                    businessLicenceImageUri = uri
                    e.printStackTrace()
                }
                businessLicenceImageUri?.apply {
                    //val file = File(this.path)
                    file?.apply {
                        //                        tvFileName.visible()
                        //                        tvFileName.text = this.name
                        //                        ivBusiness.gone()
                    }
                }
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            profileImageUri = result?.uri
            profileImageUri?.apply {
                binding.ivProfile.loadImageRound(this)
                binding.ivProfilePlus.gone()
            }
        }
    }

}