package com.mobile.travelaja.module.my_booking.eticket

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.airbnb.lottie.parser.ColorParser
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.*
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.ActivityTicketBookingBinding
import com.mobile.travelaja.module.my_booking.eticket.model.TicketResult

class TicketBookingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTicketBookingBinding
    private lateinit var ticket : TicketResult
    private var type = TRAIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ticket_booking)
        setData()
        binding.data = ticket
        binding.type = type
        setToolbar()
        setTitle()
        setSegments()
        setContentInfo()
        setPassengers()
        setShape()
    }

    private fun setToolbar(){
        binding.toolbar.setNavigationOnClickListener {
            Toast.makeText(this,"Back",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTitle(){
        val name = when(type){
            TRAIN -> "Train Itinerary"
            FLIGHT -> "Flight E-ticket"
            else -> ""
        }
        binding.tvTitleTicket.text = name
    }

    private fun setData(){

        type = TRAIN
        val data = intent.getParcelableExtra<TicketResult>("data")
        if (data != null){
            ticket = data
        }
    }

    private fun setPassengers(){
        val adapter = PassengerAdapter()
        binding.rvPassenger.adapter = adapter
        adapter.list = ticket.Passengers.toMutableList()
        adapter.notifyDataSetChanged()
    }

    private fun setSegments(){
        val adapter = TicketAdapter()
        binding.rvTicket.adapter = adapter
        adapter.list = ticket.Segments.toMutableList()
        adapter.notifyDataSetChanged()
    }

    private fun setContentInfo(){
        binding.includeHotel.root.isVisible = type == HOTEL
        binding.includeInfoImportant.root.isVisible = type == TRAIN
        binding.includeInfoPreFlight.root.isVisible = type == FLIGHT
    }

    private fun setShape(){
        val triangleEdge = TriangleEdgeTreatment(30f,true)
        val edgeTreatment = EdgeTreatment()
            edgeTreatment.getEdgePath(10f,10f,10f,ShapePath(2f,2f))
        val topEdgeTreatment = CurvedEdgeTreatment(50f,0f,0f,0f)
        val shapeAppearanceModel = ShapeAppearanceModel.builder().setLeftEdge(topEdgeTreatment).setRightEdge(topEdgeTreatment).build()
        val shape = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ColorStateList.valueOf(resources.getColor(R.color.white))
            elevation = 10F
            strokeColor = ColorStateList.valueOf(resources.getColor(R.color.black))
            strokeWidth = 0.2f
        }
        binding.viewRvTicket.background = shape
    }

    inner class CurvedEdgeTreatment(val size : Float) : EdgeTreatment() {
        override fun getEdgePath(
            length: Float,
            center: Float,
            interpolation: Float,
            shapePath: ShapePath
        ) {
            super.getEdgePath(length, center, interpolation, shapePath)
            shapePath.quadToPoint(length/2f,size * interpolation,length,0f)
        }
    }

    companion object {
        const val TRAIN = 1
        const val FLIGHT = 2
        const val HOTEL = 3
    }
}