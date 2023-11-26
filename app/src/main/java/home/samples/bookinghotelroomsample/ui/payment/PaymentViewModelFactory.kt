package home.samples.bookinghotelroomsample.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PaymentViewModelFactory (private val paymentViewModel: PaymentViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return paymentViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}