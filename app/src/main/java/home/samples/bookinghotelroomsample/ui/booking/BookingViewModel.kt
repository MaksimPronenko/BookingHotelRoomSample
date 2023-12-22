package home.samples.bookinghotelroomsample.ui.booking

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.samples.bookinghotelroomsample.data.Repository
import home.samples.bookinghotelroomsample.models.BookingData
import home.samples.bookinghotelroomsample.models.Tourist
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

    var phoneNumberState: Boolean? = null
    var emailState: Boolean? = null
    // Стутус null для поля ввода означает, что не произошла ещё первая проверка состояния при
    // снятии фокуса с поля. При последующей активации поля, проверка будет происходить уже при
    // каждом изменении символа.

//    private val _phoneNumberStateChannel = Channel<Boolean>()
//    val phoneNumberStateChannel = _phoneNumberStateChannel.receiveAsFlow()

    var bookingData: BookingData? = null

    var receivedDigits: String = "" // Полученные цифры номера без учёта +7

    var tourists: List<Tourist> = listOf(
        Tourist(
            informationHidden = false,
            firstName = null,
            firstNameFieldStatus = null,
            surname = null,
            surnameFieldStatus = null,
            birthDate = null,
            birthDateFieldStatus = null,
            citizenship = null,
            citizenshipFieldStatus = null,
            passportNumber = null,
            passportNumberFieldStatus = null,
            passportValidityPeriod = null,
            passportValidityPeriodFieldStatus = null
        )
    )
    private val _touristsChannel = Channel<List<Tourist>>()
    val touristsChannel = _touristsChannel.receiveAsFlow()

    fun loadBookingData() {
        Log.d(TAG, "loadRoomsListData() запущена")
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = BookingVMState.Loading
            Log.d(TAG, "ViewModelState.Loading")

            bookingData = repository.getBookingData()
            Log.d(TAG, bookingData.toString())

            if (bookingData == null) _state.value = BookingVMState.Error
            else {
                _state.value = BookingVMState.Loaded(phoneNumberState ?: true, emailState ?: true)
                _touristsChannel.send(element = tourists)
            }
        }
    }

    fun handleEnteredPhoneNumber() {
        viewModelScope.launch {
            phoneNumberState = receivedDigits.length == 10
            Log.d(TAG, "handleEnteredPhoneNumber(): phoneNumberState = $phoneNumberState")
            _state.value = BookingVMState.Loaded(phoneNumberState!!, emailState ?: true)
//            _phoneNumberStateChannel.send(phoneNumberState)
        }
    }

    fun handleEnteredEmail(email: String) {
        viewModelScope.launch {
            emailState = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            Log.d(TAG, "handleEnteredEmail(): email = $email; emailState = $emailState")
            _state.value = BookingVMState.Loaded(phoneNumberState ?: true, emailState!!)
        }
    }

    fun changeTouristData(position: Int, tourist: Tourist) {
        viewModelScope.launch(Dispatchers.IO) {
            val touristsMutable = tourists.toMutableList()
            touristsMutable[position] = tourist
            tourists = touristsMutable.toList()
            _touristsChannel.send(element = tourists)
        }
    }

    fun addTourist() {
        viewModelScope.launch(Dispatchers.IO) {
            val touristsMutable = tourists.toMutableList()
            touristsMutable.add(
                Tourist(
                    informationHidden = false,
                    firstName = null,
                    firstNameFieldStatus = null,
                    surname = null,
                    surnameFieldStatus = null,
                    birthDate = null,
                    birthDateFieldStatus = null,
                    citizenship = null,
                    citizenshipFieldStatus = null,
                    passportNumber = null,
                    passportNumberFieldStatus = null,
                    passportValidityPeriod = null,
                    passportValidityPeriodFieldStatus = null
                )
            )
            tourists = touristsMutable.toList()
            _touristsChannel.send(element = tourists)
        }
    }
}