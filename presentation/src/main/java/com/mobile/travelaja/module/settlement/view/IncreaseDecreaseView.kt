package com.mobile.travelaja.module.settlement.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.mobile.travelaja.R

class IncreaseDecreaseView(context: Context, attributes: AttributeSet) :
    LinearLayout(context, attributes) {
    private var increase: ImageView
    private var tvValue: TextView
    private var decrease: ImageView
    private var value = 0
    private var minValue = 0
    private var listener: IncreaseDecreaseListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.increase_decrease_layout, this, true)
        increase = findViewById(R.id.btnIncrease)
        tvValue = findViewById(R.id.tvValueIncDec)
        tvValue.text = value.toString()
        decrease = findViewById(R.id.btnDecrease)
        increase.setOnClickListener {
            increase()
        }

        decrease.setOnClickListener {
            decrease()
        }
//        tvValue.addTextChangedListener {
//            if (listener != null) {
//                listener!!.onChangeValue(it.toString().toInt())
//            }
//        }
    }

    private fun decrease() {
        val tempValue = value.dec()
        if (tempValue >= minValue) {
            value = tempValue
            tvValue.text = value.toString()
            listener?.onChangeValue(value)
        }
        enableDecrease()
    }

    private fun increase() {
        value = value.inc()
        tvValue.text = value.toString()
        listener?.onChangeValue(value)
        enableDecrease()
    }

    private fun enableDecrease() {
        decrease.isEnabled = value > minValue
    }

    fun setValue(minValue: Int, value: Int = 0, listener: IncreaseDecreaseListener?= null) {
        this.minValue = minValue
        if (value > minValue) {
            this.value = value
        } else {
            this.value = minValue
        }
        tvValue.text = this.value.toString()
        enableDecrease()
        if (this.listener == null)
            this.listener = listener
    }

    companion object {
        @BindingAdapter("app:setIncDecrease")
        @JvmStatic
        fun setIncDecrease(view: IncreaseDecreaseView, value: Int) {
            view.setValue(1, value, null)
        }
    }
}