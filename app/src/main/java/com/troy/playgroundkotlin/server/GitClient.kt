package com.troy.playgroundkotlin.server

import android.content.Context
import com.troy.playgroundkotlin.server.response.SearchUserResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class GitClient(private val context: Context) : GitClientInterface {

    companion object {
        private const val MAX_REQUESTS = 64
        private const val MAX_REQUESTS_PER_HOST = 16
    }

    private val myService: MyService

    private val isRelease = false

    init {

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = MAX_REQUESTS
        dispatcher.maxRequestsPerHost = MAX_REQUESTS_PER_HOST

        // logging interceptor
        val logging = HttpLoggingInterceptor()
        if (isRelease) {
            logging.level = HttpLoggingInterceptor.Level.NONE
        } else {
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            // for debug complete raw response data
            //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .connectTimeout(30, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .connectionPool(ConnectionPool(5, 50, TimeUnit.SECONDS))
            .addInterceptor(logging) // enable log
            .protocols(listOf(Protocol.HTTP_1_1))
            .build()

        // should not change this into main branch( ex dev-2.0 or master)
        // It can be test in local by modify settings.xml or create another branch for server build test

//        val awsAddress = context.resources.getString(R.string.server_address)
        val awsAddress = "https://api.github.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(awsAddress)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        myService = retrofit.create(MyService::class.java)
    }

    interface MyService {
        @Headers("Content-Type:application/json")
        @GET("search/users")
        fun searchUsers(@Query("q") keyword: String, @Query("page") page: Int): Single<SearchUserResponse>
    }

    override fun searchUsers(keyword: String, page: Int): Single<SearchUserResponse> {
        return myService.searchUsers(keyword, page)
    }
}
