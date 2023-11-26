package home.samples.bookinghotelroomsample.ui.room

import androidx.lifecycle.ViewModel
import home.samples.bookinghotelroomsample.ui.ViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomViewModel : ViewModel() {
    private val _state = MutableStateFlow<ViewModelState>(
        ViewModelState.Loading
    )
    val state = _state.asStateFlow()
}