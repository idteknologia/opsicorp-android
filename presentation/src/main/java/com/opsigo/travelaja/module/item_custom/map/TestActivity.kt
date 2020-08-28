package com.opsigo.travelaja.module.item_custom.map

import android.Manifest
import android.content.Intent
import android.os.Build
import com.opsigo.travelaja.R
import android.location.Location
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Constants.CODE_MAP_ACTIVITY
import com.opsigo.travelaja.utility.Constants.READ_REQUEST_LOCATION
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.layout_test.*
import opsigo.com.datalayer.mapper.Serializer

class TestActivity : BaseActivity() {

    var latitude  = -7.3589299
    var longitude = 112.6916272

    override fun getLayout(): Int { return R.layout.layout_test }

    override fun OnMain() {
//        tvPermission.setOnClickListener {
//            checkPermissionLocation()
//        }

        getAllAirLine()

    }

    val data = "{\n" +
            "  \"CabinFilterClasses\" : [],\n" +
            "  \"PreferredCarriers\" : [\"ID\"],\n" +
            "  \"origin\":\"CGK\",\n" +
            "  \"destination\":\"BDO\",\n" +
            "  \"travelAgentCode\":\"apidev\",\n" +
            "  \"returnDate\":\"\",\n" +
            "  \"jobTitleId\":\"6b6fb0ef-4d8a-4419-a38a-b84e0dc56844\",\n" +
            "  \"airline\":\"2\",\n" +
            "  \"departDate\":\"2020-09-12\",\n" +
            "  \"compCode\":\"000006\"\n" +
            "}"

    fun getAllAirLine(){
        Globals.writeJsonToFile(Serializer.serialize(data),this@TestActivity,"testJson.op")

        setLog("---------")
        if (Globals.readJsonFromFile(this,"testJson.op").isEmpty()){
            setLog("next step")
        }
        else{
            setLog(Globals.readJsonFromFile(this,"testJson.op"))
        }
    }

    fun checkWriteFilePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !== android.content.pm.PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !== android.content.pm.PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)!== android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION),
                        READ_REQUEST_LOCATION)
            }
            else {
                initLocation()
            }
        }
        else{
            initLocation()
        }
    }


    fun checkPermissionLocation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !== android.content.pm.PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !== android.content.pm.PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)!== android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION),
                        READ_REQUEST_LOCATION)
            }
            else {
                initLocation()
            }
        }
        else{
            initLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            READ_REQUEST_LOCATION -> {
                initLocation()
            }
        }
    }


    fun initLocation() {
        tv_current_lication.setOnClickListener {
            getLatLongWithGprs()
        }
        open_map.setOnClickListener {
            Constants.Latitude = latitude
            Constants.Longitude = longitude
            gotoActivityResult(MapActivity::class.java,CODE_MAP_ACTIVITY)
        }
    }

    fun getLatLongWithGprs(){
        val location = object : MyLocation.LocationResult(){
            override fun gotLocation(location: Location) {
                latitude  = location.latitude
                longitude = location.longitude
                setLog("test latlong ${latitude} - ${longitude}")
            }
        }

        MyLocation().getLocation(this,location)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            CODE_MAP_ACTIVITY -> {
                setLog(data?.getDoubleExtra("lat",0.0).toString())
                setLog(data?.getDoubleExtra("long",0.0).toString())
            }
        }
    }
}