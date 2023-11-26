package home.samples.bookinghotelroomsample.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookingViewModelFactory (private val bookingViewModel: BookingViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
            return bookingViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}