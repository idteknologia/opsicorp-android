package com.mobile.travelaja.utility

import android.text.TextUtils
import android.util.Log
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class CurrencyIDRFormatter {

    companion object {
        const val lang = "in"
        const val country = "ID"
    }

    fun format(amount: String?): String {
        var idrCurrency = ""
        try {
            val formatRp: DecimalFormatSymbols
            if (!TextUtils.isEmpty(amount)) {
                val double = amount?.toDouble()
                val resultInt = double?.toLong()

                val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
                val localeID = Locale(lang, country)
                formatRp = DecimalFormatSymbols(localeID)

                formatRp.currencySymbol = "Rp "
                formatRp.groupingSeparator = '.'
                kursIndonesia.decimalFormatSymbols = formatRp
                idrCurrency = kursIndonesia.format(resultInt)
            }
        } catch (e: NumberFormatException) {
            Log.d("CurrencyIDRFormatter", e.message ?: "number format exception")
        } catch (e: Exception) {
            Log.d("CurrencyIDRFormatter", e.message ?: "unknown error")
        } finally {
            return if (idrCurrency.contains(",")) {
                idrCurrency.substring(0, idrCurrency.length - 3)
            } else {
                idrCurrency
            }
        }
    }

    fun manageDiscountDecimal(percentage: Double?): String {
        var persen = percentage.toString()
        return if (persen.contains(".0")) {
            persen.replace(".0", "")
        } else {
            persen
        }
    }
}