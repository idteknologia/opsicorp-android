package opsigo.com.domainlayer.model.accomodation.flight


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
class SsrModel (
    var originCode      :String = "",
    var destinationCode :String = "",
    var flightNumber    :String = "",
    var mealType        :String = "",
    var drinkType       :String = "",
    var isOther         :Boolean= false,
    var isBagage        :Boolean= false,
    var isHaveSport     :Boolean = true,
    var isHaveDrink     :Boolean = true,
    var isHaveMeal      :Boolean = true,
    var isDoubleDrink   :Boolean = true,

    var bagaggeSelected : DataSsrModel = DataSsrModel(),
    var bagaggeItemSelected : ArrayList<SelectedBaggageModel> = ArrayList(),
    var ssrSelected     :ArrayList<SelectedSsrModel> = ArrayList(),
    var dataBagage      :ArrayList<DataSsrModel> = ArrayList(),
    var dataSsr         :ArrayList<SsrItemModel> = ArrayList(),
):Parcelable




