package com.mobile.travelaja.base.compose

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.mobile.travelaja.R

object AssetsUtils {
    val fontFamily = FontFamily(
        Font(R.font.lato_regular, weight = FontWeight.Normal),
        Font(R.font.lato_bold, weight = FontWeight.Bold),
        Font(R.font.lato_black, weight = FontWeight.Black)
    )
}