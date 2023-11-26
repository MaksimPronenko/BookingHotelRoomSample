package home.samples.bookinghotelroomsample.ui.booking

import androidx.lifecycle.ViewModel
import home.samples.bookinghotelroomsample.ui.ViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookingViewModel : ViewModel() {
    private val _state = MutableStateFlow<ViewModelState>(
        ViewModelState.Loading
    )
    val state = _state.asStateFlow()
}