package home.samples.bookinghotelroomsample.ui.apapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
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
    private val changeTouristData: (Int, Tourist) -> Unit,
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
                    passportNumber.isGone = true
                    passportValidityPeriod.isGone = true
                    buttonHideOrShow.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.arrow_down))
                } else {
                    firstName.isGone = false
                    surname.isGone = false
                    birthDate.isGone = false
                    citizenship.isGone = false
                    passportNumber.isGone = false
                    passportValidityPeriod.isGone = false
                    buttonHideOrShow.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.arrow_up))
                }

                firstnameEditText.setText(item.firstName ?: "")
                surnameEditText.setText(item.surname ?: "")
                birthDateEditText.setText(item.birthDate ?: "")
                citizenshipEditText.setText(item.citizenship ?: "")
                passportNumberEditText.setText(item.passportNumber ?: "")
                passportValidityPeriodEditText.setText(item.passportValidityPeriod ?: "")

                handleEditFieldStatus(firstName, item.firstNameFieldStatus ?: true)
                handleEditFieldStatus(surname, item.surnameFieldStatus ?: true)
                handleEditFieldStatus(birthDate, item.birthDateFieldStatus ?: true)
                handleEditFieldStatus(citizenship, item.citizenshipFieldStatus ?: true)
                handleEditFieldStatus(passportNumber, item.passportNumberFieldStatus ?: true)
                handleEditFieldStatus(passportValidityPeriod, item.passportValidityPeriodFieldStatus ?: true)

//                handleTextChangedListener(firstnameEditText, item, position)
//                handleTextChangedListener(surnameEditText, item, position)
//                handleTextChangedListener(birthDateEditText, item, position)
//                handleTextChangedListener(citizenshipEditText, item, position)
//                handleTextChangedListener(passportNumberEditText, item, position)
//                handleTextChangedListener(passportValidityPeriodEditText, item, position)

                firstnameEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item.firstName = s.toString()
                        if (item.firstNameFieldStatus != null) {
                            item.firstNameFieldStatus = !firstnameEditText.text.isNullOrBlank()
                            handleEditFieldStatus(firstName, item.firstNameFieldStatus ?: true)
                        }
                        changeTouristData(holder.getBindingAdapterPosition(), item)
                    }
                })

                surnameEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item.surname = s.toString()
                        if (item.surnameFieldStatus != null) {
                            item.surnameFieldStatus = !surnameEditText.text.isNullOrBlank()
                            handleEditFieldStatus(surname, item.surnameFieldStatus ?: true)
                        }
                        changeTouristData(holder.getBindingAdapterPosition(), item)
                    }
                })

                birthDateEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item.birthDate = s.toString()
                        if (item.birthDateFieldStatus != null) {
                            item.birthDateFieldStatus = !birthDateEditText.text.isNullOrBlank()
                            handleEditFieldStatus(birthDate, item.birthDateFieldStatus ?: true)
                        }
                        changeTouristData(holder.getBindingAdapterPosition(), item)
                    }
                })

                citizenshipEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item.citizenship = s.toString()
                        if (item.citizenshipFieldStatus != null) {
                            item.citizenshipFieldStatus = !citizenshipEditText.text.isNullOrBlank()
                            handleEditFieldStatus(citizenship, item.citizenshipFieldStatus ?: true)
                        }
                        changeTouristData(holder.getBindingAdapterPosition(), item)
                    }
                })

                passportNumberEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item.passportNumber = s.toString()
                        if (item.passportNumberFieldStatus != null) {
                            item.passportNumberFieldStatus = !passportNumberEditText.text.isNullOrBlank()
                            handleEditFieldStatus(passportNumber, item.passportNumberFieldStatus ?: true)
                        }
                        changeTouristData(holder.getBindingAdapterPosition(), item)
                    }
                })

                passportValidityPeriodEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item.passportValidityPeriod = s.toString()
                        if (item.passportValidityPeriodFieldStatus != null) {
                            item.passportValidityPeriodFieldStatus = !passportValidityPeriodEditText.text.isNullOrBlank()
                            handleEditFieldStatus(passportValidityPeriod, item.passportValidityPeriodFieldStatus ?: true)
                        }
                        changeTouristData(holder.getBindingAdapterPosition(), item)
                    }
                })

                buttonHideOrShow.setOnClickListener {
                    item.informationHidden = item.informationHidden != true
                    changeTouristData(position, item)
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

    private fun handleEditFieldStatus(textInputLayout: TextInputLayout, fieldStatus: Boolean) {
        if (fieldStatus) textInputLayout.boxBackgroundColor =
            context.getColor(R.color.grey_screen_background)
        else textInputLayout.boxBackgroundColor =
            context.getColor(R.color.error_background)
    }

//    private fun handleTextChangedListener(textInputEditText: TextInputEditText, tourist: Tourist, position: Int) {
//        textInputEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable?) {
//                tourist.firstName = s.toString()
//                if (tourist.firstNameFieldStatus != null) {
//                    tourist.firstNameFieldStatus = !textInputEditText.text.isNullOrBlank()
//                }
//                changeTouristData(position, tourist)
//            }
//        })
//    }
}

class TouristDataViewHolder(val binding: TouristDataItemBinding) :
    RecyclerView.ViewHolder(binding.root)

class AddTouristViewHolder(val binding: AddTouristItemBinding) :
    RecyclerView.ViewHolder(binding.root)