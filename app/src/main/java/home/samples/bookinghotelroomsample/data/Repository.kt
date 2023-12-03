package home.samples.bookinghotelroomsample.data

import android.util.Log
import home.samples.bookinghotelroomsample.api.retrofit
import home.samples.bookinghotelroomsample.models.Hotel
import home.samples.bookinghotelroomsample.models.RoomsList
import javax.inject.Inject

private const val TAG = "HotelRepository"

class Repository @Inject constructor() {
    // Если из API приходит null или возникает исключение, то функиции возвращают null.
    // По null в дальнейшем формируется статус ошибки ViewModel.

    suspend fun getHotel(): Hotel? {
        kotlin.runCatching {
            retrofit.getHotel()
        }.fold(
            onSuccess = {
                Log.d(TAG, it.toString())
                return it
            },
            onFailure = {
                Log.d(TAG, "Failure")
                return null
            }
        )
    }

    suspend fun getRoomsList(): RoomsList? {
        kotlin.runCatching {
            retrofit.getRoomsList()
        }.fold(
            onSuccess = {
                Log.d(TAG, it.toString())
                return it
            },
            onFailure = {
                Log.d(TAG, "Failure")
                return null
            }
        )
    }
}