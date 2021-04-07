package opsigo.com.datalayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class OrderDbEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "idUser")
    var idUser : String = ""

    @ColumnInfo(name = "departtemen")
    var departtemen : String = ""

    @ColumnInfo(name = "purpose")
    var purpose: String = ""

    @ColumnInfo(name = "city")
    var city :String             = ""

    @ColumnInfo(name = "date")
    var date :String             = ""

    @ColumnInfo(name = "time")
    var time :String             = ""

    @ColumnInfo(name = "statusOrder")
    var statusOrder :String      = ""

    @ColumnInfo(name = "statusTrip")
    var statusTrip :String       = ""

    @ColumnInfo(name = "totalParticipant")
    var totalParticipant :String = ""

    @ColumnInfo(name = "tripCode")
    var tripCode         :String = ""

    @ColumnInfo(name = "typeAccomodation")
    var typeAccomodation :String = ""

    @ColumnInfo(name = "listParticipant")
    var listParticipant        : String = ""

    @ColumnInfo(name = "detailListAccomodation")
    var detailListAccomodation : String = ""

    @ColumnInfo(name = "detailOrder")
    var detailOrder            : String = ""

    @ColumnInfo(name = "typeOrder")
    var typeOrder              : String = ""

    @ColumnInfo(name = "dataParticipant")
    var dataParticipant        : String = ""

    @ColumnInfo(name = "dataBisnisTrip")
    var dataBisnisTrip         : String = ""




}
