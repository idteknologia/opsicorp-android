package com.opsigo.library.model

import android.graphics.Color

data class ColorData(val defaultBgColor: Int = 0xFFFFFF, val defaultTextColor: Int = 0x999999,
                     val todayTextColor: Int = 0x222222, val prevDayTextColor: Int = 0xEEEEEE,
                     val startDayBgColor: Int = 0x628DE5, val startDayTextColor: Int = 0xFFFFFF,
                     val endDayBgColor: Int = 0x0A72CD, val endDayTextColor: Int = 0xFFFFFF,
                     val selectedDayBgColor: Int = 0xEEEEEE) {



    class Builder {
        private var defaultBgColor: Int = Color.parseColor("#FFFFFF")
        private var defaultTextColor: Int = Color.parseColor("#999999")
        private var todayTextColor: Int = Color.parseColor("#222222")
        private var prevDayTextColor: Int = Color.parseColor("#EEEEEE")
        private var startDayBgColor: Int = Color.parseColor("#ffb733")
        private var startDayTextColor: Int = Color.parseColor("#FFFFFF")
        private var endDayBgColor: Int = Color.parseColor("#ffb733")
        private var endDayTextColor: Int = Color.parseColor("#FFFFFF")
        private var selectedDayBgColor: Int = Color.parseColor("#EEEEEE")


        fun setDefaultBgColor(color: String) = apply { this.defaultBgColor = Color.parseColor(color) }

        fun setDefaultBgColor(color: Int) = apply { this.defaultBgColor = color }

        fun setDefaultTextColor(color: String) = apply { this.defaultTextColor = Color.parseColor(color) }

        fun setDefaultTextColor(color: Int) = apply { this.defaultTextColor = color }

        fun setTodayTextColor(color: String) = apply { this.todayTextColor = Color.parseColor(color) }

        fun setTodayTextColor(color: Int) = apply { this.todayTextColor = color }

        fun setPrevDayTextColor(color: String) = apply { this.prevDayTextColor = Color.parseColor(color) }

        fun setPrevDayTextColor(color: Int) = apply { this.prevDayTextColor = color }

        fun setStartDayBgColor(color: String) = apply { this.startDayBgColor = Color.parseColor(color) }

        fun setStartDayBgColor(color: Int) = apply { this.startDayBgColor = color }

        fun setStartDayTextColor(color: String) = apply { this.startDayTextColor = Color.parseColor(color) }

        fun setStartDayTextColor(color: Int) = apply { this.startDayTextColor = color }

        fun setEndDayBgColor(color: String) = apply { this.endDayBgColor = Color.parseColor(color) }

        fun setEndDayBgColor(color: Int) = apply { this.endDayBgColor = color }

        fun setEndDayTextColor(color: String) = apply { this.endDayTextColor = Color.parseColor(color) }

        fun setEndDayTextColor(color: Int) = apply { this.endDayTextColor = color }

        fun setSelectedDayBgColor(color: String) = apply { this.selectedDayBgColor = Color.parseColor(color) }

        fun setSelectedDayBgColor(color: Int) = apply { this.selectedDayBgColor = color }

        fun build() = ColorData(defaultBgColor, defaultTextColor, todayTextColor,
                prevDayTextColor, startDayBgColor, startDayTextColor, endDayBgColor, endDayTextColor,
                selectedDayBgColor)
    }
}
