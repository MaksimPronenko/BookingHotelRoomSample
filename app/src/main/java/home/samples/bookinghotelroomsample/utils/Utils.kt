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

        fun getRatingText(context: Context, rating: Int?): String {
            return when(rating) {
                1 -> "$rating " + context.getString(R.string.minimum_comfort_level)
                2 -> "$rating " + context.getString(R.string.satisfactory)
                3 -> "$rating " + context.getString(R.string.good)
                4 -> "$rating " + context.getString(R.string.great)
                5 -> "$rating " + context.getString(R.string.excellent)
                else -> context.getString(R.string.rating_unknown)
            }
        }
    }
}