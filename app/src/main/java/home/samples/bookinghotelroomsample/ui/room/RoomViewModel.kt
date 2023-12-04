package home.samples.bookinghotelroomsample.ui.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.samples.bookinghotelroomsample.data.Repository
import home.samples.bookinghotelroomsample.models.Room
import home.samples.bookinghotelroomsample.models.RoomsList
import home.samples.bookinghotelroomsample.ui.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "RoomVM"

class RoomViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<ViewModelState>(
        ViewModelState.Loading
    )
    val state = _state.asStateFlow()

    private val _rooms = MutableStateFlow<List<Room>>(emptyList())
    val rooms = _rooms.asStateFlow()

    var hotelName: String = ""

    fun loadRoomsListData() {
        Log.d(TAG, "loadRoomsListData() запущена")
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ViewModelState.Loading
            Log.d(TAG, "ViewModelState.Loading" )

            val roomsListData: RoomsList? = repository.getRoomsList()
            Log.d(TAG, roomsListData.toString())

            if (roomsListData == null) _state.value = ViewModelState.Error
            else {
                _rooms.value = roomsListData.rooms

                _state.value = ViewModelState.Loaded
                Log.d(TAG, "ViewModelState.Loaded" )
            }
        }
    }
}