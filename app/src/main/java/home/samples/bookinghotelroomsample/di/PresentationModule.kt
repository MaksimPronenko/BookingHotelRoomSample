package home.samples.bookinghotelroomsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import home.samples.bookinghotelroomsample.data.Repository
import home.samples.bookinghotelroomsample.ui.booking.BookingViewModel
import home.samples.bookinghotelroomsample.ui.booking.BookingViewModelFactory
import home.samples.bookinghotelroomsample.ui.hotel.HotelViewModel
import home.samples.bookinghotelroomsample.ui.hotel.HotelViewModelFactory
import home.samples.bookinghotelroomsample.ui.payment.PaymentViewModel
import home.samples.bookinghotelroomsample.ui.payment.PaymentViewModelFactory
import home.samples.bookinghotelroomsample.ui.room.RoomViewModel
import home.samples.bookinghotelroomsample.ui.room.RoomViewModelFactory

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {
    @Provides
    fun provideHotelViewModel(
        repository: Repository
    ): HotelViewModel {
        return HotelViewModel(
            repository
        )
    }

    @Provides
    fun provideHotelViewModelFactory(hotelViewModel: HotelViewModel): HotelViewModelFactory {
        return HotelViewModelFactory(hotelViewModel)
    }

    @Provides
    fun provideRoomViewModel(
        repository: Repository
    ): RoomViewModel {
        return RoomViewModel(
            repository
        )
    }

    @Provides
    fun provideRoomViewModelFactory(roomViewModel: RoomViewModel): RoomViewModelFactory {
        return RoomViewModelFactory(roomViewModel)
    }

    @Provides
    fun provideBookingViewModel(
        repository: Repository
    ): BookingViewModel {
        return BookingViewModel(
            repository
        )
    }

    @Provides
    fun provideBookingViewModelFactory(bookingViewModel: BookingViewModel): BookingViewModelFactory {
        return BookingViewModelFactory(bookingViewModel)
    }

    @Provides
    fun providePaymentViewModel(): PaymentViewModel {
        return PaymentViewModel()
    }

    @Provides
    fun providePaymentViewModelFactory(paymentViewModel: PaymentViewModel): PaymentViewModelFactory {
        return PaymentViewModelFactory(paymentViewModel)
    }
}