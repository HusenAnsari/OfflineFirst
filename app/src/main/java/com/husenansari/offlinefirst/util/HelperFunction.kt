package com.husenansari.offlinefirst.util

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.husenansari.offlinefirst.R

object HelperFunction {

    fun getLoader(context: Context?): Dialog {
        val loaderDialog = Dialog(context!!)
        val window = loaderDialog.window
        window!!.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawableResource(R.drawable.dialog_basic_transparent)
        loaderDialog.setContentView(R.layout.loader)
        window.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        loaderDialog.setCancelable(false)
        val progressBar = loaderDialog.findViewById<ProgressBar>(R.id.loader)
        loaderDialog.setOnShowListener { progressBar.visibility = View.VISIBLE }
        loaderDialog.setOnDismissListener { progressBar.visibility = View.GONE }
        return loaderDialog
    }

    fun hasInternetConnection(application: MyApplication): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}