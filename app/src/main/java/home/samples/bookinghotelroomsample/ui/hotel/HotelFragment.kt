package home.samples.bookinghotelroomsample.ui.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import home.samples.bookinghotelroomsample.R
import home.samples.bookinghotelroomsample.databinding.FragmentHotelBinding
import home.samples.bookinghotelroomsample.ui.ViewModelState
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HotelFragment : Fragment() {

//    companion object {
//        fun newInstance() = HotelFragment()
//    }

    @Inject
    lateinit var hotelViewModelFactory: HotelViewModelFactory
    private val viewModel: HotelViewModel by viewModels { hotelViewModelFactory }

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadHotelData()

        binding.toChoosingRoom.setOnClickListener {
            findNavController().navigate(R.id.action_HotelFragment_to_RoomFragment)
        }

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

//                                Glide
//                                    .with(binding.poster.context)
//                                    .load(viewModel.poster)
//                                    .into(binding.poster)
//                                binding.ratingAndName.text = viewModel.getRatingAndNameString()
//                                binding.yearAndGenres.text = viewModel.getYearAndGenresString()
//                                binding.countriesAndLengthAndAgeLimit.text =
//                                    viewModel.getCountriesAndLengthAndAgeLimitString()
//                                if (viewModel.shortDescription != null)
//                                    binding.shortDescription.text =
//                                        if (viewModel.shortDescriptionCollapsed)
//                                            cutText(viewModel.shortDescription!!)
//                                        else viewModel.shortDescription
//                                else {
//                                    binding.shortDescription.isGone = true
//                                    binding.blankLineBetweenTextFields.isGone = true
//                                }
//                                if (viewModel.description != null)
//                                    binding.description.text =
//                                        if (viewModel.descriptionCollapsed) cutText(viewModel.description!!)
//                                        else viewModel.description
//                                else {
//                                    binding.description.isGone = true
//                                    binding.blankLineBetweenTextFields.isGone = true
//                                }
                            }

                            ViewModelState.Error -> {
                                binding.scrollView.isGone = true
                                binding.progress.isGone = true
//                                findNavController().navigate(R.id.action_FilmFragment_to_ErrorBottomFragment)
                            }
                        }
                    }
            }
        }
    }
}