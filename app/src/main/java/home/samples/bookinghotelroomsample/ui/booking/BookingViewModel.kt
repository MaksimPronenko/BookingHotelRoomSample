package home.samples.bookinghotelroomsample.ui.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.samples.bookinghotelroomsample.data.Repository
import home.samples.bookinghotelroomsample.models.BookingData
import home.samples.bookinghotelroomsample.ui.BookingVMState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "BookingVM"

class BookingViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<BookingVMState>(
        BookingVMState.Loading
    )
    val state = _state.asStateFlow()

    var phoneNumberState: Boolean = true
    var emailState: Boolean = true

    private val _phoneNumberStateChannel = Channel<Boolean>()
    val phoneNumberStateChannel = _phoneNumberStateChannel.receiveAsFlow()

    var bookingData: BookingData? = null

    var receivedDigits: String = "" // Полученные цифры номера без учёта +7


    fun loadBookingData() {
        Log.d(TAG, "loadRoomsListData() запущена")
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = BookingVMState.Loading
            Log.d(TAG, "ViewModelState.Loading" )

            bookingData = repository.getBookingData()
            Log.d(TAG, bookingData.toString())

            if (bookingData == null) _state.value = BookingVMState.Error
            else _state.value = BookingVMState.Loaded(phoneNumberState, emailState)
        }
    }

    fun handleEnteredData() {
        viewModelScope.launch {
            phoneNumberState = receivedDigits.length == 10
            Log.d(TAG, "handleEnteredData(): phoneNumberState = $phoneNumberState")
            emailState = false
            _state.value = BookingVMState.Loaded(phoneNumberState, emailState)
            _phoneNumberStateChannel.send(phoneNumberState)
        }
    }
}