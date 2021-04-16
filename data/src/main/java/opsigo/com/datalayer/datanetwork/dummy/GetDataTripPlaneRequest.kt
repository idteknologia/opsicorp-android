package opsigo.com.datalayer.datanetwork.dummy

import opsigo.com.data.network.UrlEndpoind
import opsigo.com.datalayer.datanetwork.BaseGetData
import javax.inject.Inject

class GetDataTripPlaneRequest(baseUrl:String) : BaseGetData() {
    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

}
