package com.opsigo.travelaja.module.item_custom.map

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals


class MapActivity : BaseActivity(), OnMapReadyCallback {

    var latNow:Double? = -7.3589299
    var longNow:Double? = 112.6916272
    lateinit var map: GoogleMap
    lateinit var latLongNow : LatLng

    override fun getLayout(): Int {
        return R.layout.map_acitiviy_view
    }

    override fun OnMain() {
        setMapView()
        initDataBundle()
    }

    private fun initDataBundle() {
        if (Constants.Latitude==null||Constants.Longitude==null){
            finish()
        }
        else {
            latNow = Constants.Latitude
            longNow = Constants.Longitude
        }
    }

    override fun onMapReady(mMap: GoogleMap) {
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.stylemap))
            if(!success) {setLog("Style parsing failed.")}
        } catch (e: Resources.NotFoundException) {
            setLog( "Can't find style. Error: ")
        }

        map = mMap
        latLongNow = LatLng(latNow!!,longNow!!)
        setMap()

        map.setOnMapClickListener(object :GoogleMap.OnMapClickListener{
            override fun onMapClick(latlong: LatLng) {
                latNow = latlong.latitude
                longNow = latlong.longitude
                latLongNow = latlong
                setMap()
            }
        })


        map.uiSettings.isZoomControlsEnabled = true
        val zoomLevel = 15.0f //This goes up to 21
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLongNow, zoomLevel))

        map.setOnInfoWindowClickListener {
            val data  = Intent()
            data.putExtra("lat",latNow!!)
            data.putExtra("long",longNow!!)
            Globals.finishResultOk(this,data)
        }
    }

    private fun setMap() {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLongNow)
        markerOptions.title("Set this location !")
        map.clear()
        map.animateCamera(CameraUpdateFactory.newLatLng(latLongNow))
        map.addMarker(markerOptions)

//       set animation rond
      /*  val color: Int    = Color.BLUE
        val initialRadius = 1f
        val maxRadius     = 5000f //20000f
        val co = CircleOptions().center(latLongNow).radius(initialRadius.toDouble()).strokeColor(color).fillColor(color).strokeWidth(1.0f)
        val c: Circle     = map.addCircle(co)
        val c2: Circle    = map.addCircle(co)

        val h = Handler()
        h.postDelayed(Fader(h, c, initialRadius, maxRadius, Color.BLUE, co,map), 300)
        h.postDelayed(Fader(h, c2, initialRadius, maxRadius, Color.BLUE, co,map), 750)*/
    }

    internal class Fader(private val h: Handler,
                         private var c: Circle,
                         private val initialRadius: Float,
                         private val maxRadius: Float,
                         private val initialColor: Int,
                         private val co: CircleOptions,
                         private val map :GoogleMap) : Runnable {
        private var radius = 0f
        private var baseColor = 0
        private var color = 0
        private val radiusJump = 400f
        var numIncrements = 0
        var alphaIncrement = 0
        private fun reset() {
            radius = initialRadius
            color = initialColor
            baseColor = initialColor
            numIncrements = ((maxRadius - initialRadius) / radiusJump).toInt()
            alphaIncrement = 0x100 / numIncrements
            if (alphaIncrement <= 0) alphaIncrement = 1
        }

        override fun run() {
            var alpha = Color.alpha(color)
            radius = radius + radiusJump
            c.radius = radius.toDouble()
            alpha -= alphaIncrement
            color = Color.argb(alpha, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor))
            c.fillColor = color
            c.strokeColor = color
            if (radius < maxRadius) {
                h.postDelayed(this, 25)
            } else {
                c.remove()
                reset()
                c = map.addCircle(co)
                h.postDelayed(this, 2000)
            }
            //done
        }

        init {
            reset()
        }
    }

    @SuppressLint("ResourceType")
    private fun setMapView() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val zoomControls = mapFragment.getView()?.findViewById(0x1) as View

        for (i in 0 until (zoomControls as ViewGroup).childCount) {
            val child = zoomControls.getChildAt(i)
            if (i == 0) {
                child.visibility = View.GONE
            }
            if (i == 1) {
                child.visibility = View.GONE
            }
        }
    }

}