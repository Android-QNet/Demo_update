package com.demo.android.presentation.core.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import com.demo.android.R
import com.demo.android.presentation.home.HomeActivity
import com.demo.android.presentation.utility.AppConstant
import com.demo.android.presentation.utility.Logger
import com.demo.android.presentation.utility.PrefKeys
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.parcel.Parcelize

open class FirebaseMessaging : FirebaseMessagingService() {
    
    private val TAG = "FirebaseMessaging"
    private var mNotificationId: Int = 0
    private var mContext: Context? = null
    
    companion object {
        
        const val FCM_KEY_TITLE = "title"
        const val FCM_KEY_MESSAGE = "message"
        const val FCM_KEY_ID = "id"
        const val FCM_KEY_TYPE = "type"
        const val FCM_KEY_DATA = "data"
        const val FCM_KEY_ADDITIONAL_DATA = "additional_data"
        
        const val TYPE_PAYMENT = "Payment"
        const val TYPE_ORDER = "Order"
        const val TYPE_REGISTER = "Register"
        const val TYPE_WALLET_POINT = "Walletpoint"
        const val TYPE_ORDERSTATUS = "Orderstatus"
        const val TYPE_PROMOCODE = "Promocode"
        
        const val NOTIFICATION_ID_NEW_MESSAGE = 10005
        
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Prefs.putString(PrefKeys.PushTokenKey, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Logger.d("From : " + remoteMessage.from)
        Logger.d("getData() : " + remoteMessage.data)
        Logger.d("getNotification() : " + remoteMessage.notification)

        mContext = this
        remoteMessage.apply {
            createNotification(remoteMessage)
        }
    }
    
    private fun createNotification(remoteMessage: RemoteMessage) {
        val messageData = remoteMessage.data
        Logger.e(messageData.toString())
        
        var id: String? = ""
        var type: String? = ""
        var title: String? = ""
        var message: String? = ""
        var additionalData: String? = ""
        var notificationData: NotificationData? = null
        
        var pendingIntent: PendingIntent? = null
        
        id = messageData[FirebaseMessaging.FCM_KEY_ID]
        type = messageData[FirebaseMessaging.FCM_KEY_TYPE]
        title = messageData[FirebaseMessaging.FCM_KEY_TITLE]
        message = messageData[FirebaseMessaging.FCM_KEY_MESSAGE]
        additionalData = messageData[FirebaseMessaging.FCM_KEY_ADDITIONAL_DATA]
        
        if (additionalData?.isNotEmpty() == true) {
            notificationData = Gson().fromJson(additionalData, NotificationData::class.java)
        }
        
        mNotificationId = System.currentTimeMillis().toInt()
        
        pendingIntent = getIntentForHome(type.orEmpty(), notificationData)
        
        popupNotification(title.orEmpty(), message.orEmpty(), pendingIntent, mNotificationId)
    }
    
    private fun popupNotification(
        title: String,
        content: String,
        pendingIntent: PendingIntent?,
        notificationId: Int
    ) {
        
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    getResources(),
                    R.mipmap.ic_launcher
                )
            )
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.app_name),
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        
        notificationManager.notify(
            notificationId, notificationBuilder.build()
        )
    }
    
    private fun getIntentForHome(type: String, notificationData: NotificationData?): PendingIntent? {
        val resultIntent = Intent(this, HomeActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val bundle = Bundle()
        
        bundle.putString(AppConstant.EXTRA_TYPE, type)
        if (notificationData != null) {
            bundle.putParcelable(NotificationData::class.java.simpleName, notificationData)
        }
        
        resultIntent.putExtras(bundle)
        
        val pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

    @Parcelize
    data class NotificationData(
        @SerializedName(AppConstant.EXTRA_ID)
        var orderId: String? = ""
    ) : Parcelable
    
    
}