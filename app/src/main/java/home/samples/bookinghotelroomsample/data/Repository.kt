package home.samples.bookinghotelroomsample.data

import home.samples.bookinghotelroomsample.api.retrofit
import home.samples.bookinghotelroomsample.models.Hotel
import javax.inject.Inject

class Repository @Inject constructor() {
    // Если из API приходит null или возникает исключение, то функиции возвращают null.
    // По null в дальнейшем формируется статус ошибки ViewModel.

    suspend fun getHotel(): Hotel? {
        kotlin.runCatching {
            retrofit.getHotel()
        }.fold(
            onSuccess = {
                return it
            },
            onFailure = {
                return null
            }
        )
    }
}