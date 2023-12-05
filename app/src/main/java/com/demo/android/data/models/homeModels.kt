package com.demo.android.data.models

import com.demo.android.presentation.utility.AppConstant
import com.google.gson.annotations.SerializedName


data class LogoutPRQ(
    @SerializedName(AppConstant.authKey) val authKey: String
)

data class CMSPagePRQ(
    @SerializedName("page_code")
    var page_code: String? = ""
)

data class CMSPageRS(
    @SerializedName("data")
    var cmsData: CMSData? = null
) : BaseResponse()

data class CMSData(
    @SerializedName("page_title")
    var pageTitle: String? = "",
    @SerializedName("page_description")
    var pageDescription: String? = ""
)

data class FAQDataListRS(
    @SerializedName("data")
    var faqData: FaqData? = null
) : BaseResponse()

data class FaqData(
    @SerializedName("faqs")
    var faqs: List<FaqModel>? = listOf()
)

data class FaqModel(
    @SerializedName("question")
    var question: String? = "",
    @SerializedName("answer")
    var answer: String? = ""
)

data class ContactUsPRQ(
    @SerializedName(AppConstant.authKey) val authKey: String? = "",
    @SerializedName(AppConstant.email) var contact_email: String? = "",
    @SerializedName(AppConstant.feedback) var feedback: String? = "",
    @SerializedName(AppConstant.app_version) var vAppVersion: String? = "",
    @SerializedName(AppConstant.device_type) var eDeviceType: String? = "",
    @SerializedName(AppConstant.device_name) var vDeviceName: String? = ""
)
