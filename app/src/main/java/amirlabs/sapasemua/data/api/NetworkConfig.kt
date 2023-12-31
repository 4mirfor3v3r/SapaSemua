package amirlabs.sapasemua.data.api

import amirlabs.sapasemua.BuildConfig
import amirlabs.sapasemua.data.api.service.MainService
import amirlabs.sapasemua.data.api.service.WebSocketService
import amirlabs.sapasemua.utils.rx3.RxJava3StreamAdapterFactory
import com.google.gson.GsonBuilder
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.Authenticator
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import java.util.concurrent.TimeUnit

object NetworkConfig {

    private fun createOkHttpClient(interceptors: Array<Interceptor>, authenticator: Authenticator?, showDebugLog: Boolean): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        interceptors.forEach {
            builder.addInterceptor(it)
        }

        authenticator?.let {
            builder.authenticator(it)
        }

        if (showDebugLog) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor).build()
        }
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 30
        builder.dispatcher(dispatcher)

        return builder.build()
    }

    private fun <S> createService(serviceClass: Class<S>, okhttpClient: OkHttpClient, baseURl: String): S {

        val gson = GsonBuilder()
            .create()
        val rxAdapter = RxJava3CallAdapterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURl)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .build()

        return retrofit.create(serviceClass)
    }

    private val okHttpClient = createOkHttpClient(
        interceptors = arrayOf(AppInterceptor(), HttpLoggingInterceptor()),
        authenticator = null,
        showDebugLog = BuildConfig.DEBUG
    )

    val apiService = createService(MainService::class.java, okHttpClient, BuildConfig.BASE_URL)

    val socketService = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(BuildConfig.SOCKET_BASE_URL))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava3StreamAdapterFactory())
        .build()
        .create<WebSocketService>()
}
