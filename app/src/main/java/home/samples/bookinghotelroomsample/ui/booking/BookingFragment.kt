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
import home.samples.bookinghotelroomsample.ui.apapters.TouristsAdapter
import home.samples.bookinghotelroomsample.utils.Utils
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

    private lateinit var touristsAdapter: TouristsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        touristsAdapter = TouristsAdapter(
            context = requireContext(),
            changeTouristData = { position, tourist ->
                viewModel.changeTouristData(
                    position,
                    tourist
                )
            },
            addTourist = { viewModel.addTourist() }
        )
    }

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

        binding.touristsDataRecycler.adapter = touristsAdapter

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
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
                if (viewModel.receivedDigits.length > 10)
                    viewModel.receivedDigits = viewModel.receivedDigits.substring(0, 10)
                Log.d(TAG, "receivedDigits = ${viewModel.receivedDigits}")

                var cursorPosition = 0

                for (i in viewModel.receivedDigits.indices) {
                    when (i) {
                        in (0..2) -> {
                            resultWithMask =
                                resultWithMask.replaceRange(
                                    i,
                                    i + 1,
                                    viewModel.receivedDigits[i].toString()
                                )
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
                }

                val resultStr = resultWithMask
                Log.d(TAG, "resultStr = $resultStr")

                mSelfChange = true
                binding.phoneNumberEditText.setText(resultStr)
                binding.phoneNumberEditText.setSelection(cursorPosition)
                mSelfChange = false
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "phoneNumberEditText - afterTextChanged(s) сработала. s = $s")
                if (viewModel.phoneNumberState != null) viewModel.handleEnteredPhoneNumber()
            }
        })

        binding.phoneNumberEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && viewModel.phoneNumberState == null)
                viewModel.handleEnteredPhoneNumber()
        }

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "emailEditText - afterTextChanged(s) сработала. s = $s")
                if (viewModel.emailState != null) viewModel.handleEnteredEmail(s.toString())
            }
        })

        binding.emailEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && viewModel.emailState == null)
                viewModel.handleEnteredEmail(binding.emailEditText.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.touristsChannel.collect { tourists ->
                    touristsAdapter.setAdapterData(tourists)
                    Log.d(TAG, "Новый список коллекций: ${viewModel.tourists}")
                    pricesRefresh()
                }
            }
        }

        binding.pay.setOnClickListener {
            if (viewModel.checkTouristsData()) {
                findNavController().navigate(R.id.action_BookingFragment_to_PaymentFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.data_must_be_filled),
                    Toast.LENGTH_LONG
                ).show()
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
                                Log.d(
                                    TAG,
                                    "BookingVMState.Loaded: phone=${state.phoneNumberState}, email=${state.emailState}"
                                )
                                binding.progress.isGone = true
                                binding.scrollView.isGone = false

                                binding.ratingText.text = Utils.getRatingNumberAndText(
                                    requireContext(),
                                    viewModel.bookingData?.rating_name
                                )
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

                                pricesRefresh()
                                phoneNumberFieldRefresh(state.phoneNumberState)
                                emailFieldRefresh(state.emailState)
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

    private fun phoneNumberFieldRefresh(phoneNumberState: Boolean) {
        if (phoneNumberState) binding.phoneNumber.boxBackgroundColor =
            requireContext().getColor(R.color.grey_screen_background)
        else binding.phoneNumber.boxBackgroundColor =
            requireContext().getColor(R.color.error_background)
    }

    private fun emailFieldRefresh(emailState: Boolean) {
        if (emailState) binding.email.boxBackgroundColor =
            requireContext().getColor(R.color.grey_screen_background)
        else binding.email.boxBackgroundColor = requireContext().getColor(R.color.error_background)
    }

    private fun pricesRefresh() {
        binding.tourPrice.text = viewModel.tourPrice ?: requireContext().getString(R.string.unknown)
        binding.fuelFee.text = viewModel.fuelCharge ?: requireContext().getString(R.string.unknown)
        binding.serviceFee.text =
            viewModel.serviceCharge ?: requireContext().getString(R.string.unknown)
        binding.toBePaid.text =
            viewModel.sumToBePaid ?: requireContext().getString(R.string.unknown)
    }

    private fun getDates(start: String?, end: String?): String {
        return if (start != null && end != null) "$start - $end"
        else requireContext().getString(R.string.unknown)
    }

    private fun getNightsNumberText(nights: Int?): String {
        return if (nights != null) "$nights ${requireContext().getString(R.string.of_nights)}"
        else requireContext().getString(R.string.unknown)
    }
}