package com.tms.bucketlist.signalR;

import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import javax.net.ssl.SSLSocketFactory
import com.google.gson.internal.LinkedTreeMap

/**
 * Представляет SignalR-реализацию слушателя внешних событий.
 * @param sslSocketFactory Модифицированный протокол безопасной передачи данных.
 * @param trustAllCerts Модифицированный X509TrustManager для доверия всем сертификатам.
 */
class SignalRListener (
        private val sslSocketFactory:SSLSocketFactory,
        private val trustAllCerts: TrustAllCerts
) : ISignalRListener {

        //Адрес api
        private val apiUrl = "https://something.com/api/"

        private val hubConnection = HubConnectionBuilder.create(apiUrl + "GatewayHub")
                .setHttpClientBuilderCallback {
                        it.sslSocketFactory(sslSocketFactory, trustAllCerts)
                }
                .build()

        override val connectionId: String?
                get() = hubConnection.connectionId
        override val connectionState: HubConnectionState
                get() = hubConnection.connectionState

        override fun startConnection() {
                hubConnection.start()
        }

        override fun stopConnection() {
                hubConnection.stop()
        }

        override fun subscribe( eventName: String, handler: (event: LinkedTreeMap<String, String>) -> Unit) {
                hubConnection.on( eventName, handler, LinkedTreeMap::class.java)
        }

        override fun unsubscribe(eventName: String) {
                hubConnection.remove(eventName)
        }
}