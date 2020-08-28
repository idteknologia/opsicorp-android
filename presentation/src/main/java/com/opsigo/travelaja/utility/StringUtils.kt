package com.opsigo.travelaja.utility

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class StringUtils {

    fun setUppercaseFirstLetter(str: String): String {
        var str = str
        str = str.toLowerCase()
        return str.substring(0, 1).toUpperCase() + str.substring(1)
    }

    fun setCurrency(currency: String, d: Double, withComma: Boolean): String {
        val svSE: DecimalFormat// = new DecimalFormat("#,###.00");
        if (withComma) {
            svSE = DecimalFormat("#,###.00")
        } else {
            svSE = DecimalFormat("#,###")
        }

        val symbols = DecimalFormatSymbols(Locale("in", "ID"))
        symbols.decimalSeparator = ','
        //symbols.setGroupingSeparator(' ');
        svSE.decimalFormatSymbols = symbols
        //Log.i("currency", ": " + svSE.format(d));
        return currency + " " + svSE.format(d)
    }

    fun removeSpaces(str: String): String {
        var str = str
        str = str.replace(" ", "")
        return str
    }
}