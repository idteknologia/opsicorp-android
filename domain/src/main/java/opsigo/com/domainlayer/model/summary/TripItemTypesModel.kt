package opsigo.com.domainlayer.model.summary

data class TripItemTypesModel (
    var type: Int? = null,
    var tripItems: List<TripItemsModel>,//? = null,
    //var tripItems: List<TripItemsModel?>? = null,
//    var items: List<Any?>? = null,
    var id: String? = null,
    var name: String? = null
)