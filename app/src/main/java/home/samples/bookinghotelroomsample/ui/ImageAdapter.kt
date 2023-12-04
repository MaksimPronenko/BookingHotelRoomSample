package home.samples.bookinghotelroomsample.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import home.samples.bookinghotelroomsample.databinding.PagerHotelImageItemBinding

class ImageAdapter : RecyclerView.Adapter<ImageViewHolder>() {
    private var data: List<String> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            PagerHotelImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(pagerImage.context)
                    .load(it)
                    .placeholder(androidx.constraintlayout.widget.R.color.material_grey_300)
                    .into(pagerImage)
            }
        }
    }
}

class ImageViewHolder(val binding: PagerHotelImageItemBinding) :
    RecyclerView.ViewHolder(binding.root)