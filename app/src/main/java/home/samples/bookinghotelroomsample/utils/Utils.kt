package home.samples.bookinghotelroomsample.utils

import android.content.Context
import android.util.DisplayMetrics
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel
import home.samples.bookinghotelroomsample.R

const val ARG_HOTEL_NAME = "hotelName"

class Utils{
    companion object {
        fun createPeculiarityChip(context: Context, peculiarity: String): Chip {
            return Chip(context).apply {
                text = peculiarity
                setChipBackgroundColorResource(R.color.grey_peculiarity_chip)
                isCloseIconVisible = false
                isClickable = false
                chipStrokeWidth = 0F
                shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(convertDpToPixel(5F, context))
                chipStartPadding = convertDpToPixel(10F, context)
                chipEndPadding = convertDpToPixel(10F, context)
                setTextAppearance(R.style.ChipTextAppearance)
            }
        }

        private fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }
}