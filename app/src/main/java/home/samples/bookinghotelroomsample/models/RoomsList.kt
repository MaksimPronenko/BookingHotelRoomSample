package home.samples.bookinghotelroomsample.models

data class RoomsList(
    val rooms: List<Room>
)

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    val price_per: String,
    val peculiarities: List<String>,
    val image_urls: List<String>
)