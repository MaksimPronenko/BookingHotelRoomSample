package home.samples.bookinghotelroomsample.ui.booking

sealed class BookingVMState {
    object Loading : BookingVMState()
    data class Loaded(val phoneNumberState: Boolean, val emailState: Boolean) : BookingVMState()
    object Error : BookingVMState()
}
