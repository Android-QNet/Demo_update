package com.demo.android.domain.network



import com.demo.android.data.models.BaseResponse
import com.demo.android.data.models.CMSPageRS
import com.demo.android.data.models.FAQDataListRS
import com.demo.android.data.models.PersonListRS
import com.demo.android.presentation.utility.ApiConstant
import com.demo.android.presentation.utility.AppConstant
import com.demo.android.presentation.utility.PrefKeys
import retrofit2.http.*

interface HomeApiService {



    @FormUrlEncoded
    @POST(ApiConstant.ApiLogout)
    suspend fun performLogout(
        @Field(AppConstant.authKey) vAuthKey: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): BaseResponse

    @FormUrlEncoded
    @POST(ApiConstant.ApiGetPages)
    suspend fun getCMSPages(
        @Field(AppConstant.page_code) page_code: String,
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): CMSPageRS

    @FormUrlEncoded
    @POST(ApiConstant.ApiFaqsList)
    suspend fun getFaqs(
        @Field(AppConstant.language) language: String = PrefKeys.getLanguageSelectedCapital()
    ): FAQDataListRS

    @FormUrlEncoded
    @POST(ApiConstant.ApiContactUs)
    suspend fun performContactUs(
        @Field(AppConstant.authKey) vAuthKey: String? = "",
        @Field(AppConstant.email) contact_email: String,
        @Field(AppConstant.feedback) feedback: String,
        @Field(AppConstant.app_version) app_version: String,
        @Field(AppConstant.device_type) device_type: String,
        @Field(AppConstant.device_name) device_name: String
    ): BaseResponse
}