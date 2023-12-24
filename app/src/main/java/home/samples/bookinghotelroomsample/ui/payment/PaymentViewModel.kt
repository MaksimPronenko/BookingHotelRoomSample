package home.samples.bookinghotelroomsample.ui.payment

import androidx.lifecycle.ViewModel
import java.util.Random

class PaymentViewModel : ViewModel() {
    // Генерация кода заказа в пределах от 1 до 999999 включительно.
    val orderCode = (Random().nextInt(999999) + 1).toString()
}