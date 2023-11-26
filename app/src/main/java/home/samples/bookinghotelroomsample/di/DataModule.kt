package home.samples.bookinghotelroomsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import home.samples.bookinghotelroomsample.data.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return Repository()
    }
}