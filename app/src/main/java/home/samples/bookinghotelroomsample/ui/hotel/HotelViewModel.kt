package home.samples.bookinghotelroomsample.ui.hotel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.samples.bookinghotelroomsample.data.Repository
import home.samples.bookinghotelroomsample.models.Hotel
import home.samples.bookinghotelroomsample.ui.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "HotelVM"

class HotelViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<ViewModelState>(
        ViewModelState.Loading
    )
    val state = _state.asStateFlow()

    var name: String? = null
    var adress: String? = null
    var minimalPrice: Int? = null
    var priceForIt: String? = null
    var rating: Int? = null
    var imageUrls: List<String>? = null
    var description: String? = null
    var peculiarities: List<String>? = null

    private val _hotelImages = MutableStateFlow<List<String>>(emptyList())
    val hotelImages = _hotelImages.asStateFlow()

    fun loadHotelData() {
        Log.d(TAG, "Функция loadHotelData() запущена")
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ViewModelState.Loading
            Log.d(TAG, "ViewModelState.Loading" )

            val hotelData: Hotel? = repository.getHotel()
            Log.d(TAG, hotelData.toString())

            if (hotelData == null) _state.value = ViewModelState.Error
            else {
//                name = hotelData.name
//                adress = hotelData.adress
//                minimalPrice = hotelData.minimal_price
//                priceForIt = hotelData.price_for_it
//                rating = hotelData.rating
//                imageUrls = hotelData.image_urls
//                description = hotelData.about_the_hotel.description
//                peculiarities = hotelData.about_the_hotel.peculiarities

                _hotelImages.value = imageUrls.orEmpty()

                _state.value = ViewModelState.Loaded
                Log.d(TAG, "ViewModelState.Loaded" )
            }
        }
    }
}