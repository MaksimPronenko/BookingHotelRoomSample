package home.samples.bookinghotelroomsample.ui

sealed class BookingVMState {
    object Loading : BookingVMState()
    data class Loaded(val phoneNumberState: Boolean, val emailState: Boolean) : BookingVMState()
    object Error : BookingVMState()
}
