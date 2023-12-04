package home.samples.bookinghotelroomsample.ui.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import home.samples.bookinghotelroomsample.databinding.RoomItemBinding
import home.samples.bookinghotelroomsample.models.Room
import home.samples.bookinghotelroomsample.ui.ImageAdapter
import home.samples.bookinghotelroomsample.utils.Utils

class RoomAdapter(
    val context: Context,
    private val onClick: (Room) -> Unit
): RecyclerView.Adapter<RoomViewHolder>() {
    private var data: List<Room> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Room>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            RoomItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            item?.let { room ->
                val hotelImageAdapter = ImageAdapter()
                this.roomImagePager.adapter = hotelImageAdapter
                hotelImageAdapter.setData(room.image_urls)
                roomName.text = room.name
                room.peculiarities.forEach {
                    peculiaritiesGroup.addView(Utils.createPeculiarityChip(context, it))
                }
                price.text = room.price.toString()
                pricePer.text = room.price_per
            }
        }
        holder.binding.chooseRoom.setOnClickListener{
            item?.let{
                onClick(item)
            }
        }
    }
}

class RoomViewHolder(val binding: RoomItemBinding) :
    RecyclerView.ViewHolder(binding.root)