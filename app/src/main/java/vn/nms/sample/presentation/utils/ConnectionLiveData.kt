package vn.nms.sample.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

    val validateNetworkConnections = hashSetOf<Network>()
    var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    fun announceStatus() {
        if (validateNetworkConnections.isNotEmpty()) {
            postValue(true)
        } else {
            postValue(false)
        }
    }

    private fun getConnectivityManagerCallback() =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                val hasNetworkConnection =
                    networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
                            && networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
                if (hasNetworkConnection) {
                    if (!validateNetworkConnections.contains(network)){
                        validateNetworkConnections.add(network)
                        announceStatus()
                    }
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                if (validateNetworkConnections.contains(network)){
                    validateNetworkConnections.remove(network)
                }
                announceStatus()
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                ) {
                    if (!validateNetworkConnections.contains(network)){
                        validateNetworkConnections.add(network)
                        announceStatus()
                    }
                } else {
                    if (validateNetworkConnections.contains(network)){
                        validateNetworkConnections.remove(network)
                    }
                }

            }
        }


    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }


}