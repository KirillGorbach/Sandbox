package com.sandbox.fragments.retrofit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.InputStream


interface ImgService {
    @GET("images/thumb/c/cc/%D0%A4%D0%BE%D1%82%D0%BE_%D0%81%D0%B6%D0%B8%D0%BA%D0%B0_%D1%81_%D0%90%D1%80%D0%B1%D1%83%D0%B7%D0%BE%D0%BC.jpg/1152px-%D0%A4%D0%BE%D1%82%D0%BE_%D0%81%D0%B6%D0%B8%D0%BA%D0%B0_%D1%81_%D0%90%D1%80%D0%B1%D1%83%D0%B7%D0%BE%D0%BC.jpg")
    fun getImg() : Call<ResponseBody>
}

class ImageClient {

    fun getImg(callback: (Bitmap?) -> Unit) {
        imgService.getImg().enqueue(object : Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val inputStream: InputStream = response.body()!!.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    callback(bitmap)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()
        })
    }

    companion object{
        lateinit var INSTANCE: ImageClient
        fun getInstance(): ImageClient {
            INSTANCE = ImageClient()
            return INSTANCE
        }
    }


    private fun getHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val request: Request = chain.request()
                val newRequest: Request = request.newBuilder().build()
                chain.proceed(newRequest)
            })
            .build()
    }

    private val imgService =
        Retrofit.Builder()
            .baseUrl("https://ezhepedia.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build().create(ImgService::class.java)

}