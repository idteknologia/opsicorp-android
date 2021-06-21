package com.mobile.travelaja.module.home.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mobile.travelaja.R

class ItemContentImageRect @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle) {
    private var imageView : ImageView
    private var tvTitle : TextView
    private var tvMore : TextView

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.item_content_image, this, true)
        imageView = findViewById(R.id.ivContent)
        tvTitle = findViewById(R.id.tvTitle)
        tvMore = findViewById(R.id.tvReadMore)
    }

    fun setContent(title : String, image : Int){
        tvTitle.text = title
        imageView.setImageResource(image)
    }

    fun setContent(title : String, image : Int,clickListener: OnClickListener){
        tvTitle.text = title
        imageView.setImageResource(image)
        tvMore.setOnClickListener(clickListener)
    }
}