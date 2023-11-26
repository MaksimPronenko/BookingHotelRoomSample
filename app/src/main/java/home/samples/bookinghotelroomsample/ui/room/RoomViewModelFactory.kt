package home.samples.bookinghotelroomsample.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RoomViewModelFactory (private val roomViewModel: RoomViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return roomViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}