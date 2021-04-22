package com.opsigo.travelaja.utility

enum class TripTypeEnum(var s: String) {
    UDARA("UDARA"),
    NONUDARA("NON-UDARA");

    override fun toString(): String {
        return s
    }

}