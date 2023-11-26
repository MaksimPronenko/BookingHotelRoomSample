package home.samples.bookinghotelroomsample.ui.payment

import androidx.lifecycle.ViewModel
import home.samples.bookinghotelroomsample.ui.ViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentViewModel : ViewModel() {
    private val _state = MutableStateFlow<ViewModelState>(
        ViewModelState.Loading
    )
    val state = _state.asStateFlow()
}