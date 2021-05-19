package opsigo.com.datalayer.datanetwork

import com.google.gson.JsonObject
import opsigo.com.data.network.UrlEndpoind
import opsigo.com.datalayer.mapper.*
import okhttp3.ResponseBody
import opsigo.com.datalayer.model.accomodation.hotel.city.CityHotelEntity
import opsigo.com.datalayer.model.accomodation.hotel.confirmation.ConfirmationHotelEntity
import opsigo.com.datalayer.model.accomodation.hotel.country.CountryHotelEntity
import opsigo.com.datalayer.model.accomodation.hotel.detail.HotelDetailEntity
import opsigo.com.datalayer.model.accomodation.hotel.room.RoomHotelEntity
import opsigo.com.datalayer.model.accomodation.hotel.search_hotel.SearchHotelEntity
import opsigo.com.datalayer.model.accomodation.flight.ProgressFlightEnitity
import opsigo.com.datalayer.model.accomodation.flight.reservation.ReservationFlightEntity
import opsigo.com.datalayer.model.accomodation.flight.validation.ValidationFlightEntity
import opsigo.com.datalayer.model.accomodation.reasoncode.ResponseReasonCodeEntity
import opsigo.com.datalayer.model.accomodation.train.ProgressTrainEnitity
import opsigo.com.datalayer.model.accomodation.train.reservation.BookingTrainEntity
import opsigo.com.datalayer.model.accomodation.train.search.SearchTrainResultEntity
import opsigo.com.datalayer.model.accomodation.train.seat.get_seat.SeatMapTrainEntity
import opsigo.com.datalayer.model.accomodation.train.validation.ValidationTrainEntity
import opsigo.com.domainlayer.callback.*
import opsigo.com.domainlayer.callback.CallbackReasonCode
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.domainlayer.usecase.AccomodationRepository
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class GetDataAccomodation(baseUrl:String) : BaseGetData(), AccomodationRepository {

    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

    override fun getDataStationTrain(token: String, callback: CallbackDestinationAccomodation) {
        apiOpsicorp.getDataStationTrain(token).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(AccomodationDestinationMapper().mapping(JSONArray(responseString)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(messageFailed)
                }
            }
        })
    }

    override fun getSearchFlight(token: String, data: HashMap<Any, Any>, callback: CallbackResultSearchFlight) {
        apiOpsicorp.getDataSearchFlight(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(AccomodationResultFlightMapper().mapping(responseString!!))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(messageFailed)
                }
            }
        })
    }

    override fun getPreferedFlight(token: String, data: HashMap<Any, Any>, callback: CallbackAirlinePreference) {
        apiOpsicorp.getAirlineprefered(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(CodeAirlinePrefMapper().mapping(responseString!!))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getAllCodeFlight(token: String, callback: CallbackGetAllCodeAirline) {
        apiOpsicorp.getAllCodeAirline(token).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(CodeAirlineMapper().mapping(responseString!!))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(messageFailed)
                }
            }
        })
    }

    override fun getSearchTrain(token: String, data: HashMap<Any, Any>, callbackResult: CallbackResultSearchTrain) {
        apiOpsicorp.getDataSearchTrain(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackResult.failed(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val model = Serializer.deserialize(responseString!!, SearchTrainResultEntity::class.java)
                        if (model.result.outgoingTrain.orEmpty().isEmpty()){
                            callbackResult.success(ArrayList())
                        }
                        else{
                            callbackResult.success(AccomodationResultTrainMapper().mapping(model))
                        }
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackResult.failed(message)
                    }
                }catch (e:Exception){
                    callbackResult.failed(messageFailed)
                }
            }
        })
    }

    override fun getReasonCodeTrain(token: String,typeAccomodation: String,callback: CallbackReasonCode){
        apiOpsicorp.getReasonCode(token,typeAccomodation).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(ReasonCodeMapper().mapping(Serializer.deserialize(responseString!!,ResponseReasonCodeEntity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(messageFailed)
                }
            }
        })
    }

    override fun seatMapTrain(token: String, data: HashMap<Any, Any>, callback: CallbackSeatMap) {
        apiOpsicorp.getSeatMapTrain(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val jsonArray  = JSONArray(responseString)
                        val data = ArrayList<SeatMapTrainEntity>()
                        data.clear()
                        for (i in 0 until jsonArray.length()){
                            data.add(Serializer.deserialize(jsonArray.getString(i), SeatMapTrainEntity::class.java))
                        }
                        callback.success(SeatMapTrainMapper().mapper(data))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }


    override fun getSeatMapFlight(token: String, data: HashMap<Any, Any>, callback: CallbackSeatMapFlight) {
        apiOpsicorp.getSeatMapFlight(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(SeatMapFlightMapper().mapper(responseString.toString()))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getSsrFlight(token: String, data: HashMap<Any, Any>, callback: CallbackGetSsr) {
        apiOpsicorp.getSsrFlight(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(GetSsrMapper().mapper(responseString.toString()))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getFareRules(token: String, data: HashMap<Any, Any>, callback: CallbackGetFareRules) {
        apiOpsicorp.getFareRules(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(GetFareRulesMapper().mapper(responseString!!))
                    } else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                } catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }

        })
    }

    override fun getValidationTrain(token: String, data: HashMap<Any, Any>, callback: CallbackValidationTrain) {
        apiOpsicorp.getValidationTrain(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(ValidationMapper().mapper(Serializer.deserialize(responseString!!,ValidationTrainEntity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getValidationFlight(token: String, data: HashMap<Any, Any>, callback: CallbackValidationFlight) {
        apiOpsicorp.getValidationFlight(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(ValidationFlightMapper().mapper(Serializer.deserialize(responseString!!, ValidationFlightEntity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getReservationFlight(token: String, data: HashMap<Any, Any>, callback: CallbackReserveFlight) {
        apiOpsicorp.reserveFlight(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string().toString()
                        if (responseString=="{\"status\":false,\"errorMessage\":\"\"}"){
                            callback.failedLoad(JSONObject(responseString).getString("errorMessage"))
                        }
                        else{
                            callback.successLoad(ReserveFlightMapper().mapper(Serializer.deserialize(responseString, ReservationFlightEntity::class.java)))
                        }
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message.toString())
                }
            }
        })
    }

    override fun getReservationTrain(token: String, data: HashMap<Any, Any>, callback: CallbackReservationTrain) {
        apiOpsicorp.reservationTrain(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        if (responseString != null) {
                            if (responseString.contains("\"status\":false,\"errorMessage\":\"\"")){
                                callback.failedLoad(responseString.toString())
                            }else {
                                if (responseString.contains("\"isSuccess\":0")){
                                    callback.successLoad(ReservationMapper().mapper(Serializer.deserialize(responseString!!,BookingTrainEntity::class.java)))
                                }
                                else{
                                    callback.failedLoad(JSONObject(responseString).getString("errorMessage"))
                                }
                            }
                        }
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun setSeatMapTrain(token: String, data: HashMap<Any, Any>, callback: CallbackSetSeatMapTrain) {
        apiOpsicorp.setSeatMapTrain(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(SetSeatMapMapper().mapper(responseString!!))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("errorMessage")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getProgressTrain(token: String, idTrain: String, callback: CallbackProgressTrain) {
        apiOpsicorp.getProgressTrain(token,idTrain).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(ProgressMapper().mapperTrain(Serializer.deserialize(responseString!!,ProgressTrainEnitity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getRemoveTrain(token: String, idTrain: HashMap<Any,Any>, callback: CallbackArrayListString) {
        apiOpsicorp.getRemoveTrain(token,idTrain).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val json = JSONObject(responseString)
                        if (json.getBoolean("isSuccess").equals(true)){
                            callback.successLoad(ArrayList())
                        }
                        else{
                            callback.failedLoad(json.getString("errorMessage"))
                        }
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getSyncTrain(token: String, data: HashMap<Any,Any>, callback: CallbackArrayListString) {
        apiOpsicorp.getSyncTrain(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val json = JSONObject(responseString)
                        if (json.getString("errorMessage").isEmpty()){
                            val pr = ArrayList<String>()
                            pr.add(json.getJSONObject("progress").getString("Progress"))
                            pr.add(json.getJSONObject("progress").getString("Text"))
                            callback.successLoad(pr)
                        }
                        else{
                            callback.failedLoad(json.getString("errorMessage"))
                        }
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getProgressFlight(token: String, idFlight: String, callback: CallbackProgressFlight) {
        apiOpsicorp.getProgressFlight(token,idFlight).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
//                        callback.success(ProgressMapper().mapperTrain(Serializer.deserialize(responseString!!,ProgressTrainEnitity::class.java)))
                        callback.success(ProgressMapper().mapperFlight(Serializer.deserialize(responseString!!,ProgressFlightEnitity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getSearchCountry(token: String, travelAgent: String, callback: CallbackArrayListCountry) {
        apiOpsicorp.getSearchCountry(token,travelAgent).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(CountryHotelMapper().mapping(Serializer.deserialize(responseString!!,CountryHotelEntity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getSearchCity(token: String, isoCountry: String, travelAgent: String, callback: CallbackListCityHotel) {
        apiOpsicorp.getSearchCityHotel(token,isoCountry,travelAgent).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(CityHotelMapper().mapping(Serializer.deserialize(responseString!!,CityHotelEntity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getSearchHotel(token: String, data: HashMap<Any, Any>, callback: CallbackSearchHotel) {
        apiOpsicorp.getSearchHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val data = SearchHotelMapper().mapping(Serializer.deserialize(responseString!!,SearchHotelEntity::class.java))
                        callback.success(data.first,data.second)
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getSearchPageHotel(token: String, data: HashMap<Any, Any>, callback: CallbackSearchHotel) {
        apiOpsicorp.getPageHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val data = SearchHotelMapper().mapping(Serializer.deserialize(responseString!!,SearchHotelEntity::class.java))
                        callback.success(data.first,data.second)
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getHotelDetail(token: String, data: HashMap<Any, Any>, callback: CallbackDetailHotel) {
        apiOpsicorp.getDetailHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(HotelDetailMapper().mapping(Serializer.deserialize(responseString!!, HotelDetailEntity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getCancellationPolicyHotel(token: String, data: HashMap<Any, Any>, callback: CallbackRoomHotel) {
        apiOpsicorp.getCacellationPolicyHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(RoomMapperHotel().mapping(Serializer.deserialize(responseString!!,RoomHotelEntity::class.java)))
                    }
                    else {
                        val json    = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getConfirmationHotel(token: String, data: HashMap<Any, Any>, callback: CallbackConfirmationHotel) {
        apiOpsicorp.getConfirmationHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.code()==200){
                        val responseString = response.body()?.string()
                        callback.success(ConfirmationHotelMapper().mapping(Serializer.deserialize(responseString!!,ConfirmationHotelEntity::class.java)))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getValidationHotel(token: String, data: HashMap<Any, Any>, callback: CallbackValidationHotel) {
        apiOpsicorp.getValidationHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(ValidationHotelMapper().mapping(responseString))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getBookingHotel(token: String, data: HashMap<Any, Any>, callback: CallbackBookingHotel) {
        apiOpsicorp.getBookingHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val responseString = response.body()?.string()
                    if (JSONObject(responseString).getBoolean("isSuccess")){
                        callback.success(BookingHotelMapper().mapping(responseString.toString()))
                    }
                    else {
                        callback.failed(JSONObject(responseString).getString("errorMessage"))
                    }
                }
                else {
                    val json = JSONObject(response.errorBody()?.string())
                    val message = json.optString("error_description")
                    callback.failed(message)
                }
                try {

                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getSyncHotel(token: String, data: HashMap<Any, Any>, callback: CallbackArrayListString) {
        apiOpsicorp.getSyncHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val json = JSONObject(responseString)
                        if (json.getBoolean("isSuccess")){
                            val pr = ArrayList<String>()
                            pr.add("Success")
                            callback.successLoad(pr)
                        }
                        else{
                            callback.failedLoad("Error sync")
                        }
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }


    override fun getUpdateRemarkHotel(token: String, data: HashMap<Any, Any>, callback: CallbackUploadFile) {
        apiOpsicorp.getUpdateRemarkHotel(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(UploadModel())
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getRemoveHotel(token: String, tripItemId: String, tripId: String, hotelId: String, pnrId: String, travelAgent: String, callback: CallbackUploadFile) {
        apiOpsicorp.getRemoveHotel(token,tripItemId,tripId,hotelId,pnrId,travelAgent).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(UploadModel())
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }

    override fun getListCompanyHotel(token: String, notEmptyLatlong: Boolean, callback: CallbackListCompany) {
        apiOpsicorp.getListCompany(token,notEmptyLatlong).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.success(ListCompanyMapper().mapping(responseString))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(e.message!!)
                }
            }
        })
    }

    override fun getSyncronizeFlight(token: String, data: HashMap<Any, Any>, callback: CallbackArrayListString) {
        apiOpsicorp.syncronizeFlight(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val json = JSONObject(responseString)
                        if (json.getString("errorMessage").isEmpty()){
                            val pr = ArrayList<String>()
                            pr.add(json.getJSONObject("progress").getString("Progress"))
                            pr.add(json.getJSONObject("progress").getString("Text"))
                            callback.successLoad(pr)
                        }
                        else{
                            callback.failedLoad(json.getString("errorMessage"))
                        }

                        /*"\"isSuccess\": true,\n" +
                                "    \"errorMessage\": \"\",\n" +
                                "    \"progress\": {\n" +
                                "        \"Key\": \"fdcf92b8-58dd-4cb2-8a04-944db798320b\",\n" +
                                "        \"PnrId\": \"60562226-81ce-4876-813b-3f9e17aa5437\",\n" +
                                "        \"ProgressNum\": 100.0,\n" +
                                "        \"Progress\": \"100\",\n" +
                                "        \"Text\": \"Reserved\",\n" +
                                "        \"JobType\": \"Sync\",\n" +
                                "        \"RunStart\": \"0001-01-01 00:00:00\",\n" +
                                "        \"RunEnd\": \"0001-01-01 00:00:00\",\n" +
                                "        \"IsManual\": false,\n" +
                                "        \"ReferenceCode\": null,\n" +
                                "        \"PnrCode\": \"C6Q69X\",\n" +
                                "        \"PartitionKey\": \"20200624\",\n" +
                                "        \"RowKey\": \"f8530484-1fe6-4012-9b18-0afad71d18de\",\n" +
                                "        \"Timestamp\": \"0001-01-01 00:00:00\",\n" +
                                "        \"ETag\": null\n" +
                                "    }"*/
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(e.message!!)
                }
            }
        })
    }


}
