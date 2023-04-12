package com.tms.bucketlist.signalR

import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

/**
 * Представляет приложение.
 */
class SignalRTest {

    lateinit var signalRListener: ISignalRListener   // Слушатель внешних событий.
    lateinit var signalRService: ISignalRService   // Сервис подписок на ресурсы.

    fun onCreate() {

        // Модифицированный X509TrustManager для доверия всем сертификатам.
        val trustAllCerts = TrustAllCerts()
        // Модифицированный протокол безопасной передачи данных.
        val sslSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            init(null, arrayOf<TrustManager>(trustAllCerts), SecureRandom())
        }.socketFactory

        signalRListener = SignalRListener(sslSocketFactory, trustAllCerts)
        signalRService = SignalRService(signalRListener)
    }

}