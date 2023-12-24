package home.samples.bookinghotelroomsample.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import home.samples.bookinghotelroomsample.R
import home.samples.bookinghotelroomsample.databinding.FragmentPaymentBinding
import javax.inject.Inject

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory
    private val viewModel: PaymentViewModel by viewModels { paymentViewModelFactory }

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.super1.setOnClickListener {
            findNavController().navigate(R.id.action_PaymentFragment_to_HotelFragment)
        }

        val orderConfirmationText = requireContext().getString(R.string.order_confirmation_part_1) +
                viewModel.orderCode + " ${requireContext().getString(R.string.order_confirmation_part_2)}"
        binding.orderConfirmation.text = orderConfirmationText
    }
}