package com.demo.android.presentation.home.activities

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.demo.android.R
import com.demo.android.databinding.ActivityCmsBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.home.HomeViewModel
import com.demo.android.presentation.utility.AppConstant
import com.demo.android.presentation.utility.setHtmlString
import org.koin.android.viewmodel.ext.android.viewModel

class CMSActivity : BaseActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getBaseViewModel() = homeViewModel

    private lateinit var binding: ActivityCmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {

        setupToolbar(
            binding.llToolbarMain.toolbar, getString(R.string.app_name), true, Color.WHITE,
            ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_arrow_back_white
        )

        attachObserver()
        initClickListener()

        val cmsType = intent.getStringExtra(AppConstant.cmsType)
        getAboutUsData(cmsType.orEmpty())
    }

    private fun attachObserver() {
        homeViewModel.cmsPageRSLiveData.observe(this, Observer {
            it?.apply {
                hideProgress()
                this.cmsData?.apply {
                    setupToolbar(
                        binding.llToolbarMain.toolbar, this.pageTitle.orEmpty(), true, Color.WHITE,
                        ContextCompat.getColor(this@CMSActivity, R.color.colorPrimary), R.drawable.ic_arrow_back_white
                    )
                    binding.txtDescription.setHtmlString(this.pageDescription.orEmpty())
                }
            }
        })
    }

    private fun getAboutUsData(cmsType: String) {
        showProgress()
        homeViewModel.getCMSPageData(cmsType)
    }

    private fun initClickListener() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}


