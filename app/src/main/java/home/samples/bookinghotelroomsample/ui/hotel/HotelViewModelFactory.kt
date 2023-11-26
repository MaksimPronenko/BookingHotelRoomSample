package home.samples.bookinghotelroomsample.ui.hotel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HotelViewModelFactory (private val hotelViewModel: HotelViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HotelViewModel::class.java)) {
            return hotelViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}