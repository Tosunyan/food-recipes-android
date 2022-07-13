package com.example.foodRecipes.domain.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    private fun getConnectivityStatus(context: Context): NetworkType {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        if (activeNetwork != null) {
            when (activeNetwork.type) {
                ConnectivityManager.TYPE_WIFI -> return NetworkType.WIFI
                ConnectivityManager.TYPE_MOBILE -> return NetworkType.MOBILE_DATA
            }
        }

        return NetworkType.NOT_CONNECTED
    }

    fun Context.hasConnection() =
        getConnectivityStatus(this) != NetworkType.NOT_CONNECTED

    enum class NetworkType {
        WIFI,
        MOBILE_DATA,
        NOT_CONNECTED
    }
}