package home.samples.bookinghotelroomsample.ui.room

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint
import home.samples.bookinghotelroomsample.databinding.FragmentRoomBinding
import home.samples.bookinghotelroomsample.models.Room
import home.samples.bookinghotelroomsample.ui.ViewModelState
import home.samples.bookinghotelroomsample.utils.convertDpToPixel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "RoomFragment"

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

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

//        binding.chooseRoom.setOnClickListener {
//            findNavController().navigate(R.id.action_RoomFragment_to_BookingFragment)
//        }

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

//                                viewModel.rooms.onEach {
//                                    Log.d(TAG, "Список комнат: $it")
//                                    it.forEach {
//                                        binding.rooms.addView(createRoomsTableRow(requireContext(), it))
//                                        addRow(it, reqy)
//                                    }
//                                }
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

//    private fun addRow(context: Context, room: Room) {
//        binding.rooms
//        val tableLayout = findViewById(R.id.table) as TableLayout
//        val inflater: LayoutInflater? = getSystemService(context, LayoutInflater::class.java)
//        val tr = inflater?.inflate(home.samples.bookinghotelroomsample.R.layout.table_row_room, null) as TableRow
//        tr.context.roomName
//        tv.setText(cell0)
//        tv = tr.getChildAt(1) as TextView
//        tv.setText(cell1)
//        binding.rooms.addView(tr)
//    }

//    private fun createRoomsTableRow(context: Context, room: Room): TableRow {
//        return TableRow(context).apply {
//            text = peculiarity
//            setChipBackgroundColorResource(home.samples.bookinghotelroomsample.R.color.grey_peculiarity_chip)
//            isCloseIconVisible = false
//            isClickable = false
//            chipStrokeWidth = 0F
//            shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(convertDpToPixel(5F, context))
//            chipStartPadding = convertDpToPixel(10F, context)
//            chipEndPadding = convertDpToPixel(10F, context)
//            setTextAppearance(home.samples.bookinghotelroomsample.R.style.ChipTextAppearance)
//        }
//    }
}