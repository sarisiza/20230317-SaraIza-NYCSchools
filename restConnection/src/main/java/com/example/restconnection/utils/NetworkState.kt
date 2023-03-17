package com.example.restconnection.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Class NetworkState
 * @author Sara Iza
 * @param connectivityManager : ConnectivityManager
 */

class NetworkState(
    private val connectivityManager: ConnectivityManager
) {

    /**
     * Function isInternetEnabled
     * Checks if there is internet connection available
     * To do network calls
     */
    fun isInternetEnabled(): Boolean =
        connectivityManager
            .getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)?:false

}