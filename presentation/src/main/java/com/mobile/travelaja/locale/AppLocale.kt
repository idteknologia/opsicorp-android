package com.mobile.travelaja.locale

import com.mobile.travelaja.R
import com.mobile.travelaja.base.InitApplications

enum class AppLocale(val value: Int) {
    English(0),
    Bahasa(1);


    val title: Int
        get() {
            return when (this) {
                English -> R.string.english
                Bahasa -> R.string.bahasa_indonesia
            }
        }

    val lang: String
        get() {
            return when (this) {
                English -> "en"
                Bahasa -> "in"
            }
        }

    override fun toString(): String {
        return InitApplications.appContext.getString(title)
    }

    companion object {
        fun fromValue(value: Int): AppLocale {
            return AppLocale.values().first { it.value == value }
        }
    }
}


