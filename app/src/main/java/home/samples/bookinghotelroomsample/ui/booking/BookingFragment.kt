package home.samples.bookinghotelroomsample.ui.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import home.samples.bookinghotelroomsample.R
import home.samples.bookinghotelroomsample.databinding.FragmentBookingBinding
import javax.inject.Inject

private const val TAG = "BookingFragment"

@AndroidEntryPoint
class BookingFragment: Fragment() {

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
    }
}