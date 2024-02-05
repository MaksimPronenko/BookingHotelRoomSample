package home.samples.bookinghotelroomsample.ui.room

import android.os.Bundle
import android.util.Log
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
import home.samples.bookinghotelroomsample.databinding.FragmentRoomBinding
import home.samples.bookinghotelroomsample.ui.ViewModelState
import home.samples.bookinghotelroomsample.ui.apapters.RoomAdapter
import home.samples.bookinghotelroomsample.utils.ARG_HOTEL_NAME
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject



private const val TAG = "RoomFragment"

@AndroidEntryPoint
class RoomFragment : Fragment() {

    @Inject
    lateinit var roomViewModelFactory: RoomViewModelFactory
    private val viewModel: RoomViewModel by viewModels { roomViewModelFactory }

    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hotelName = arguments?.getString(ARG_HOTEL_NAME) ?: ""
        viewModel.hotelName = hotelName

        roomAdapter = RoomAdapter(requireContext()) { onRoomChosenClick() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Функция onViewCreated() запущена")
        viewModel.loadRoomsListData()

        binding.rooms.adapter = roomAdapter

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
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
                                binding.rooms.isGone = true
                            }

                            ViewModelState.Loaded -> {
                                binding.progress.isGone = true
                                binding.rooms.isGone = false

                                binding.hotelName.text = viewModel.hotelName

                                viewModel.rooms.onEach {
                                    roomAdapter.setData(it)
                                }.launchIn(viewLifecycleOwner.lifecycleScope)
                            }

                            ViewModelState.Error -> {
                                binding.rooms.isGone = true
                                binding.progress.isGone = true
                            }
                        }
                    }
            }
        }
    }

    private fun onRoomChosenClick() {
        findNavController().navigate(R.id.action_RoomFragment_to_BookingFragment)
    }
}