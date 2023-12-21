package home.samples.bookinghotelroomsample.ui.apapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import home.samples.bookinghotelroomsample.R
import home.samples.bookinghotelroomsample.databinding.AddTouristItemBinding
import home.samples.bookinghotelroomsample.databinding.TouristDataItemBinding
import home.samples.bookinghotelroomsample.models.EmptyRecyclerItem
import home.samples.bookinghotelroomsample.models.RecyclerItem
import home.samples.bookinghotelroomsample.models.Tourist

private const val TAG = "TouristsAdapter"
const val VIEW_TYPE_TOURIST = 0
const val VIEW_TYPE_ADD_TOURIST = 1

class TouristsAdapter(
    val context: Context,
    private val hideOrShow: () -> Unit,
    private val addTourist: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<RecyclerItem> = listOf()
    private var mutableData: MutableList<RecyclerItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setAdapterData(receivedData: List<Tourist>) {
        Log.d(TAG, "В адаптер пришёл список размера: ${receivedData.size}")
        Log.d(TAG, "В адаптер пришёл список: $receivedData")
        mutableData = receivedData.toMutableList()
        mutableData.add(EmptyRecyclerItem())
        data = mutableData.toList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return if (position != data.lastIndex) VIEW_TYPE_TOURIST
        else VIEW_TYPE_ADD_TOURIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TOURIST) {
            TouristDataViewHolder(
                TouristDataItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            AddTouristViewHolder(
                AddTouristItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.getOrNull(position)
        if (holder is TouristDataViewHolder && item is Tourist) {
            with(holder.binding) {
                val touristNumberText = when(position) {
                    0 -> context.getString(R.string.first)
                    1 -> context.getString(R.string.second)
                    2 -> context.getString(R.string.third)
                    3 -> context.getString(R.string.fourth)
                    4 -> context.getString(R.string.fifth)
                    5 -> context.getString(R.string.sixth)
                    6 -> context.getString(R.string.seventh)
                    7 -> context.getString(R.string.eighth)
                    8 -> context.getString(R.string.ninth)
                    else -> context.getString(R.string.tenth)
                } + " ${context.getString(R.string.tourist)}"
                touristTitle.text = touristNumberText

                if (item.informationHidden == true) {
                    firstName.isGone = true
                    surname.isGone = true
                    birthDate.isGone = true
                    citizenship.isGone = true
                    passportNumberEditText.isGone = true
                    passportValidityPeriodEditText.isGone = true
                } else {
                    firstName.isGone = false
                    surname.isGone = false
                    birthDate.isGone = false
                    citizenship.isGone = false
                    passportNumberEditText.isGone = false
                    passportValidityPeriodEditText.isGone = false
                }

                firstnameEditText.setText(item.firstName ?: "")
                surnameEditText.setText(item.surname ?: "")
                birthDateEditText.setText(item.birthDate ?: "")
                citizenshipEditText.setText(item.citizenship ?: "")
                passportNumberEditText.setText(item.passportNumber ?: "")
                passportValidityPeriodEditText.setText(item.passportValidityPeriod ?: "")

                buttonHideOrShow.setOnClickListener {
                    hideOrShow()
                }
            }
        }
        if (holder is AddTouristViewHolder && item is EmptyRecyclerItem) {
            with(holder.binding) {
                buttonAddTourist.setOnClickListener {
                    addTourist()
                }
            }
        }
    }
}

class TouristDataViewHolder(val binding: TouristDataItemBinding) :
    RecyclerView.ViewHolder(binding.root)

class AddTouristViewHolder(val binding: AddTouristItemBinding) :
    RecyclerView.ViewHolder(binding.root)