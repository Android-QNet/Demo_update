package com.demo.android.presentation.utility


import android.util.Log
import com.demo.android.BuildConfig

class Logger {
    companion object {

        private val TAG: String = "Capermint"

        fun d(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, msg)
            }
        }

        fun e(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, msg)
            }
        }

        fun i(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, msg)
            }
        }

        fun w(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.w(TAG, msg)
            }
        }

    }
}