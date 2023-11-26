package home.samples.bookinghotelroomsample.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import home.samples.bookinghotelroomsample.R
import home.samples.bookinghotelroomsample.databinding.FragmentRoomBinding
import javax.inject.Inject

@AndroidEntryPoint
class RoomFragment: Fragment() {

    companion object {
        fun newInstance() = RoomFragment()
    }

    @Inject
    lateinit var roomViewModelFactory: RoomViewModelFactory
    private val viewModel: RoomViewModel by viewModels { roomViewModelFactory }

    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chooseRoom.setOnClickListener {
            findNavController().navigate(R.id.action_RoomFragment_to_BookingFragment)
        }
    }
}