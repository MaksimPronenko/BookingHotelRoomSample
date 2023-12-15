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
import home.samples.bookinghotelroomsample.ui.ViewModelState
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "BookingFragment"

@AndroidEntryPoint
class BookingFragment : Fragment() {

//    companion object {
//        fun newInstance() = BookingFragment()
//    }

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
                val receivedDigits: String = s.replace(Regex("(\\D*)"), "")
                Log.d(TAG, "receivedDigits = $receivedDigits")

                var cursorPosition = 0

                for (i in receivedDigits.indices) {
                    when (i) {
                        in (0..2) -> {
                            resultWithMask =
                                resultWithMask.replaceRange(i, i + 1, receivedDigits[i].toString())
                            cursorPosition = i + 1
                        }

                        in (3..5) -> {
                            resultWithMask = resultWithMask.replaceRange(
                                i + 2,
                                i + 3,
                                receivedDigits[i].toString()
                            )
                            cursorPosition = i + 3
                        }

                        6, 7 -> {
                            resultWithMask = resultWithMask.replaceRange(
                                i + 3,
                                i + 4,
                                receivedDigits[i].toString()
                            )
                            cursorPosition = i + 4
                        }

                        8, 9 -> {
                            resultWithMask = resultWithMask.replaceRange(
                                i + 4,
                                i + 5,
                                receivedDigits[i].toString()
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

//        binding.phoneNumberEditText.onFocusChangeListener = View.OnFocusChangeListener() {
//            override fun onFocusChange(View v, boolean hasFocus) {
//                if (v == editText && hasFocus == false) {
//                    ....
//                }
//            }
//        };

        binding.phoneNumberEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Toast.makeText(requireContext(), "Got the focus", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Lost the focus", Toast.LENGTH_LONG).show()
            }
        }


//        binding.phoneNumberEditText.setOnEditorActionListener(TextView.OnEditorActionListener {
//            override fun onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                    actionId == EditorInfo.IME_ACTION_DONE ||
//                    event != null &&
//                    event.getAction() == KeyEvent.ACTION_DOWN &&
//                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    if (event == null || !event.isShiftPressed()) {
//                        return true
//                    }
//                }
//                return false
//            }
//        })

//        binding.phoneNumberEditText.setOnFo {
//            val receivedDigits: String = s.replace(Regex("(\\D*)"), "")
//            if (receivedDigits.length < 9) binding.phoneNumberEditText.setBackgroundColor(
//                requireContext().getColor(R.color.redtest)
//            )
//        }
//
//        binding.phoneNumberEditText.onFocusChangeListener = (object : View.OnFocusChangeListener {
//            override fun onFocusChange(View v, boolean hasFocus) {
//
//                // When focus is lost check that the text field has valid values.
//
//                if (!hasFocus) {
//                    {
//                        // Validate youredittext
//                    }
//                }
//            });
//
//            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
//                    if (event == null || !event.isShiftPressed) {
//                        // the user is done typing.
//                        return true // consume.
//                    }
//                }
//                return false // pass on to other listeners.
//            }
//        })
        statesProcessing()
    }

    private fun statesProcessing() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .collect { state ->
                        when (state) {
                            ViewModelState.Loading -> {
                                binding.progress.isGone = false
                                binding.scrollView.isGone = true
                            }

                            ViewModelState.Loaded -> {
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
                            }

                            ViewModelState.Error -> {
                                binding.scrollView.isGone = true
                                binding.progress.isGone = true
                            }
                        }
                    }
            }
        }
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