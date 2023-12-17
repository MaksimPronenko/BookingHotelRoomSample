package home.samples.bookinghotelroomsample.ui.booking

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import home.samples.bookinghotelroomsample.R
import home.samples.bookinghotelroomsample.databinding.FragmentBookingBinding
import home.samples.bookinghotelroomsample.ui.BookingVMState
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "BookingFragment"

@AndroidEntryPoint
class BookingFragment : Fragment() {

    @Inject
    lateinit var bookingViewModelFactory: BookingViewModelFactory
    private val viewModel: BookingViewModel by viewModels { bookingViewModelFactory }

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Функция onViewCreated() запущена")
        viewModel.loadBookingData()

        binding.pay.setOnClickListener {
            findNavController().navigate(R.id.action_BookingFragment_to_PaymentFragment)
        }

        binding.phoneNumberEditText.addTextChangedListener(object : TextWatcher {
            private var mSelfChange = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || mSelfChange) {
                    return
                }

                // Маска без префикса "+7 ("
                var resultWithMask = requireContext().getString(R.string.phone_number_mask_short)
                // Индексы цифр в записи номера: 0, 1, 2, 5, 6, 7, 9, 10, 12, 13

                // Принятые цифры из поля без префикса "+7 ("
                viewModel.receivedDigits = s.replace(Regex("(\\D*)"), "")
                Log.d(TAG, "receivedDigits = ${viewModel.receivedDigits}")

                var cursorPosition = 0

                for (i in viewModel.receivedDigits.indices) {
                    when (i) {
                        in (0..2) -> {
                            resultWithMask =
                                resultWithMask.replaceRange(i, i + 1, viewModel.receivedDigits[i].toString())
                            cursorPosition = i + 1
                        }

                        in (3..5) -> {
                            resultWithMask = resultWithMask.replaceRange(
                                i + 2,
                                i + 3,
                                viewModel.receivedDigits[i].toString()
                            )
                            cursorPosition = i + 3
                        }

                        6, 7 -> {
                            resultWithMask = resultWithMask.replaceRange(
                                i + 3,
                                i + 4,
                                viewModel.receivedDigits[i].toString()
                            )
                            cursorPosition = i + 4
                        }

                        8, 9 -> {
                            resultWithMask = resultWithMask.replaceRange(
                                i + 4,
                                i + 5,
                                viewModel.receivedDigits[i].toString()
                            )
                            cursorPosition = i + 5
                        }
                    }
                    Log.d(TAG, "resultWithMask = $resultWithMask")
                }

                val resultStr = resultWithMask
                Log.d(TAG, "resultStr = $resultStr")

                mSelfChange = true
                binding.phoneNumberEditText.setText(resultStr)
                binding.phoneNumberEditText.setSelection(cursorPosition)
                mSelfChange = false
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged(s) сработала. s = $s")
            }
        })

        binding.phoneNumberEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Toast.makeText(requireContext(), "Phone number editing in process", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Phone number editing finished", Toast.LENGTH_LONG).show()
                viewModel.handleEnteredData()
                fieldsStatesProcessing(viewModel.phoneNumberState, viewModel.emailState)
            }
        }

        viewModelStatesProcessing()
    }

    private fun viewModelStatesProcessing() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .collect { state ->
                        when (state) {
                            BookingVMState.Loading -> {
                                binding.progress.isGone = false
                                binding.scrollView.isGone = true
                            }

                            is BookingVMState.Loaded -> {
                                binding.progress.isGone = true
                                binding.scrollView.isGone = false

                                binding.ratingText.text = viewModel.bookingData?.rating_name
                                    ?: requireContext().getString(R.string.rating_unknown)
                                binding.hotelName.text =
                                    viewModel.bookingData?.hotel_name ?: requireContext().getString(
                                        R.string.name_unknown
                                    )
                                binding.hotelAddress.text = viewModel.bookingData?.hotel_adress
                                    ?: requireContext().getString(R.string.address_unknown)
                                binding.departure.text = viewModel.bookingData?.departure
                                    ?: requireContext().getString(R.string.unknown)

                                binding.countryCity.text = viewModel.bookingData?.arrival_country
                                    ?: requireContext().getString(R.string.unknown)
                                binding.dates.text = getDates(
                                    viewModel.bookingData?.tour_date_start,
                                    viewModel.bookingData?.tour_date_stop
                                )
                                binding.numberOfNights.text =
                                    getNightsNumberText(viewModel.bookingData?.number_of_nights)
                                binding.hotel.text = viewModel.bookingData?.hotel_name
                                    ?: requireContext().getString(R.string.unknown)
                                binding.room.text = viewModel.bookingData?.room
                                    ?: requireContext().getString(R.string.unknown)
                                binding.nutrition.text = viewModel.bookingData?.nutrition
                                    ?: requireContext().getString(R.string.unknown)

                                fieldsStatesProcessing(state.phoneNumberState, state.emailState)
                            }

                            BookingVMState.Error -> {
                                binding.scrollView.isGone = true
                                binding.progress.isGone = true
                            }
                        }
                    }
            }
        }
    }

    private fun fieldsStatesProcessing(phoneNumberState: Boolean, emailState: Boolean) {
        if(phoneNumberState) binding.phoneNumber.boxBackgroundColor = requireContext().getColor(R.color.grey_screen_background)
        else binding.phoneNumber.boxBackgroundColor = requireContext().getColor(R.color.error_background)

        if(emailState) binding.mail.boxBackgroundColor = requireContext().getColor(R.color.grey_screen_background)
        else binding.phoneNumber.boxBackgroundColor = requireContext().getColor(R.color.error_background)
    }

    private fun getDates(start: String?, end: String?): String {
        return if (start != null && end != null) "$start - $end"
        else requireContext().getString(home.samples.bookinghotelroomsample.R.string.unknown)
    }

    private fun getNightsNumberText(nights: Int?): String {
        return if (nights != null) "$nights ${requireContext().getString(R.string.of_nights)}"
        else requireContext().getString(R.string.unknown)
    }
}