package com.opsigo.travelaja.module.home.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.item_content_image.view.*

class ItemContentImageRect @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle) {
    private lateinit var imageView : ImageView
    private lateinit var tvTitle : TextView

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.item_content_image, this, true)
        imageView = findViewById(R.id.ivContent)
        tvTitle = findViewById(R.id.tvTitle)
    }

    fun setContent(title : String, image : Int){
        tvTitle.text = title
        imageView.setImageResource(image)
    }

}