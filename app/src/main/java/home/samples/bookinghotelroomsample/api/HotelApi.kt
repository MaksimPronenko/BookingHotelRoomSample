package home.samples.bookinghotelroomsample.api

import home.samples.bookinghotelroomsample.models.Hotel
import home.samples.bookinghotelroomsample.models.RoomsList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface HotelApi {
    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel(): Hotel?

    @GET("/v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRoomsList(): RoomsList?
}

val retrofit: HotelApi = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://run.mocky.io")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(HotelApi::class.java)