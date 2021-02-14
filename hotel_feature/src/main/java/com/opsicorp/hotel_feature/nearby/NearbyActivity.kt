package com.opsicorp.hotel_feature.parent

import android.content.Intent
import android.view.View
import org.json.JSONArray
import android.text.Editable
import android.text.TextWatcher
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import opsigo.com.domainlayer.model.signin.CountryModel
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import kotlinx.android.synthetic.main.nearby_hotel_view.*
import opsigo.com.domainlayer.callback.CallbackListCompany
import opsigo.com.domainlayer.callback.CallbackDataAirport
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.opsicorp.hotel_feature.adapter.NearbyCityAdapter
import opsigo.com.domainlayer.callback.CallbackListCityHotel
import com.opsicorp.hotel_feature.adapter.NearbyOfficeAdapter
import com.opsicorp.hotel_feature.adapter.NearbyCountryAdapter
import com.opsicorp.hotel_feature.adapter.NearbyAirportAdapter
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.accomodation.hotel.CityHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyOfficeModel
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyAirportModel
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel

class NearbyActivity : BaseActivity() {
    override fun getLayout(): Int { return R.layout.nearby_hotel_view }

    var officeData = ArrayList<NearbyOfficeModel>()
    var countryData = ArrayList<SelectNationalModel>()
    var airportData = ArrayList<NearbyAirportModel>()
    var cityData = ArrayList<CityHotelModel>()
    var typeSelect = Constants.SELECT_NEARBY_OFFICE
    var countryName = "Indonesia"
    var idCountry   = ""

    val countryAdapter by lazy { NearbyCountryAdapter(this,countryData) }
    val cityAdapter      by lazy { NearbyCityAdapter(this,cityData) }
    val airportAdapter by lazy { NearbyAirportAdapter(this,airportData) }
    val officeAdapter   by lazy { NearbyOfficeAdapter(this,officeData) }

    override fun OnMain() {
        initRecyclerView()
//        getDataListCompany()
        viewTypeSelected()
        initFilter()
    }

    val filterAirport: ArrayList<NearbyAirportModel> = ArrayList()
    val filterCity: ArrayList<CityHotelModel> = ArrayList()
    val filterCountry: ArrayList<SelectNationalModel> = ArrayList()
    val filterOffice: ArrayList<NearbyOfficeModel> = ArrayList()
    var filterActif = false

    private fun initFilter() {
        et_filter.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filterActif = p0.toString().length>0

                when(typeSelect){
                    Constants.SELECT_NEARBY_AIRPORT -> {
                        rv_nearby.adapter = airportAdapter
                        if (p0.toString().length>0){
                            filterAirport.clear()
                            filterAirport.addAll(airportData.filter { it.nameCountry.toLowerCase().contains(p0.toString().toLowerCase())||it.nameAirport.toLowerCase().contains(p0.toString().toLowerCase())  })
                            airportAdapter.setData(filterAirport)
                        }else{
                            airportAdapter.setData(airportData)
                        }
                    }
                    Constants.SELECT_NEARBY_CITY -> {
                        rv_nearby.adapter = cityAdapter
                        if (p0.toString().length>0){
                            filterCity.clear()
                            filterCity.addAll(cityData.filter { it.cityName.toLowerCase().contains(p0.toString().toLowerCase())||it.descrip.toLowerCase().contains(p0.toString().toLowerCase())  })
                            cityAdapter.setData(filterCity)
                        }else{
                            cityAdapter.setData(cityData)
                        }
                    }
                    Constants.SELECT_NEARBY_COUNTRY -> {
                        rv_nearby.adapter = countryAdapter
                        if (p0.toString().length>0){
                            filterCountry.clear()
                            filterCountry.addAll(countryData.filter { it.name.toLowerCase().contains(p0.toString().toLowerCase())  })
                            countryAdapter.setData(filterCountry)
                        }else{
                            countryAdapter.setData(countryData)
                        }
                    }
                    Constants.SELECT_NEARBY_OFFICE -> {
                        rv_nearby.adapter = officeAdapter
                        if (p0.toString().length>0){
                            filterOffice.clear()
                            filterOffice.addAll(officeData.filter { it.nameCompany.toLowerCase().contains(p0.toString().toLowerCase())})
                            officeAdapter.setData(filterOffice)
                        }else {
                            officeAdapter.setData(officeData)
                        }
                    }

                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun viewTypeSelected() {
        typeSelect = intent.getIntExtra(Constants.TYPE_SELECT_NEARBY,Constants.SELECT_NEARBY_CITY)
        when(typeSelect){
            Constants.SELECT_NEARBY_COUNTRY -> {
                setAdapterByCountry()
                line_bottom.visibility = View.GONE
            }
            Constants.SELECT_NEARBY_OFFICE -> {
                setAdapterByOffice()
                line_bottom.visibility = View.VISIBLE
            }
            Constants.SELECT_NEARBY_AIRPORT -> {
                setAdapeterByAirport()
                line_bottom.visibility = View.VISIBLE
            }
        }

        btn_switch.setOnClickListener {
            if (btn_switch.isChecked){
                setAdapeterByAirport()
                typeSelect = Constants.SELECT_NEARBY_AIRPORT

            }
            else {
                typeSelect = Constants.SELECT_NEARBY_OFFICE
                setAdapterByOffice()
            }
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_nearby.layoutManager = layoutManager
        rv_nearby.itemAnimator = DefaultItemAnimator()
    }

    fun setAdapterByCountry(){
        rv_nearby.adapter = countryAdapter
        countryAdapter.setOnclickListener(onclickCountry)
        setDataCountry()
    }

    fun setAdapterByCity(idCountry: String){
        typeSelect = Constants.SELECT_NEARBY_CITY
        rv_nearby.adapter = cityAdapter
        cityAdapter.setOnclickListener(onclickCity)
        getDataCity(idCountry)
    }

    fun setAdapeterByAirport(){
        title_neatby.text    = getString(R.string.string_title_nearby_aiport)
        descript_neatby.text = getString(R.string.descript_nearby_airport)
        rv_nearby.adapter = airportAdapter
        airportAdapter.setOnclickListener(onclickAirport)
        getDataAirport()
    }

    private fun getDataAirport() {
        viewShowLoading()
        airportData.clear()
        GetDataTripPlane(getBaseUrl()).getDataAiport(getToken(),object :CallbackDataAirport{
            override fun success(data: ArrayList<NearbyAirportModel>) {
                airportData.clear()
                airportData.addAll(data)
                airportAdapter.setData(airportData)
                hideLoading()
            }

            override fun failed(message: String) {
                hideLoading()
                showAllert("Sorry",message)
            }
        })
    }

    fun setAdapterByOffice(){
        title_neatby.text    = getString(R.string.string_title_nearby_office)
        descript_neatby.text = getString(R.string.descript_nearby_office)
        rv_nearby.adapter = officeAdapter
        officeAdapter.setOnclickListener(onclickOffice)
        getDataOffice()
    }

    var onclickCountry = object :OnclickListenerRecyclerView{
        override fun onClick(views: Int, position: Int) {
            if (countryData.isNotEmpty()){
                if (filterActif){
                    countryName = filterCountry[position].name
                    idCountry   = filterCountry[position].id
                    val dataSelectCountry = filterCountry[position].id
                    setAdapterByCity(dataSelectCountry)
                }else {
                    countryName = countryData[position].name
                    idCountry   = countryData[position].id
                    val dataSelectCountry = countryData[position].id
                    setAdapterByCity(dataSelectCountry)
                }
            }
        }
    }

    var onclickCity = object :OnclickListenerRecyclerView{
        override fun onClick(views: Int, position: Int) {
            val data = Intent()
            data.putExtra(Constants.TYPE_SELECT_NEARBY,Constants.SELECT_NEARBY_CITY)
            data.putExtra(Constants.KeyBundle.KEY_NAME_COUNTRY,countryName)
            data.putExtra(Constants.KeyBundle.KEY_ID_COUNTRY,idCountry)
            if (filterActif){
                data.putExtra(Constants.KeyBundle.KEY_NAME_CITY,filterCity[position].cityName)
                data.putExtra(Constants.KeyBundle.KEY_ID_CITY,filterCity[position].idCity)
            }
            else{
                data.putExtra(Constants.KeyBundle.KEY_NAME_CITY,cityData[position].cityName)
                data.putExtra(Constants.KeyBundle.KEY_ID_CITY,cityData[position].idCity)
            }
            Globals.finishResultOk(this@NearbyActivity,data)
        }
    }

    var onclickAirport = object :OnclickListenerRecyclerView{
        override fun onClick(views: Int, position: Int) {
            val data = Intent()
            data.putExtra(Constants.TYPE_SELECT_NEARBY,Constants.SELECT_NEARBY_AIRPORT)
            if (filterActif){
                data.putExtra(Constants.KeyBundle.KEY_NAME_AIRPORT,filterAirport[position].nameAirport)
                data.putExtra(Constants.KeyBundle.KEY_ID_COUNTRY,filterAirport[position].countryCode)
                data.putExtra(Constants.KeyBundle.KEY_LATITUDE,filterAirport[position].latitude)
                data.putExtra(Constants.KeyBundle.KEY_LONGITUDE,filterAirport[position].longitude)
            }
            else {
                data.putExtra(Constants.KeyBundle.KEY_NAME_AIRPORT,airportData[position].nameAirport)
                data.putExtra(Constants.KeyBundle.KEY_ID_COUNTRY,airportData[position].countryCode)
                data.putExtra(Constants.KeyBundle.KEY_LATITUDE,airportData[position].latitude)
                data.putExtra(Constants.KeyBundle.KEY_LONGITUDE,airportData[position].longitude)
            }

            Globals.finishResultOk(this@NearbyActivity,data)
        }
    }

    var onclickOffice = object :OnclickListenerRecyclerView{
        override fun onClick(views: Int, position: Int) {
            val data = Intent()
            data.putExtra(Constants.KeyBundle.KEY_ID_COUNTRY,idCountry)
            data.putExtra(Constants.TYPE_SELECT_NEARBY,Constants.SELECT_NEARBY_OFFICE)
            if (filterActif){
                data.putExtra(Constants.KeyBundle.KEY_NAME_OFFICE,filterOffice[position].nameCompany)
                data.putExtra(Constants.KeyBundle.KEY_ID_COUNTRY,filterOffice[position].countryCode)
                data.putExtra(Constants.KeyBundle.KEY_LATITUDE,filterOffice[position].latitude)
                data.putExtra(Constants.KeyBundle.KEY_LONGITUDE,filterOffice[position].longitude)
            }
            else{
                data.putExtra(Constants.KeyBundle.KEY_NAME_OFFICE,officeData[position].nameCompany)
                data.putExtra(Constants.KeyBundle.KEY_ID_COUNTRY,officeData[position].countryCode)
                data.putExtra(Constants.KeyBundle.KEY_LATITUDE,officeData[position].latitude)
                data.putExtra(Constants.KeyBundle.KEY_LONGITUDE,officeData[position].longitude)
            }
            Globals.finishResultOk(this@NearbyActivity,data)
        }
    }

    fun viewShowLoading(){
        layOverlay.visibility = View.VISIBLE
    }

    fun hideLoading(){
        layOverlay.visibility = View.GONE
    }


    private fun setDataCountry() {
        if (Constants.COUNTRY_HOTEL.isNotEmpty()){
            countryData.clear()
            val listdata = JSONArray(Globals.getDataPreferenceString(this,Constants.COUNTRY_HOTEL))
            for (i in 0 until listdata.length()){
                val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
                val model = SelectNationalModel()
                model.name      = mData.name
                model.id        = mData.id
                model.callCode  = mData.callCode
                countryData.add(model)
                countryAdapter.setData(countryData)
            }
        }
    }

    private fun getDataCity(idCountry: String) {
        viewShowLoading()
        GetDataAccomodation(getBaseUrl()).getSearchCity(getToken(),idCountry,Globals.getConfigCompany(this).defaultTravelAgent,object : CallbackListCityHotel {
            override fun successLoad(data: ArrayList<CityHotelModel>) {
                cityData.clear()
                cityData.addAll(data)
                cityAdapter.setData(cityData)
                hideLoading()
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    fun getDataOffice(){
        viewShowLoading()
        GetDataAccomodation(getBaseUrl()).getListCompanyHotel(getToken(),true,object : CallbackListCompany {
            override fun success(data: ArrayList<NearbyOfficeModel>) {
                officeData.clear()
                officeData.addAll(data)
                officeAdapter.setData(officeData)
                hideLoading()
            }

            override fun failed(message: String) {
                hideLoading()
                Globals.showAlert("Sorry",message,this@NearbyActivity)
            }
        })
    }

}