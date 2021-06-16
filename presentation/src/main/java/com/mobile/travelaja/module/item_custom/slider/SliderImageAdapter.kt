package com.mobile.travelaja.module.item_custom.slider


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_slider_image_opsicorp.view.*

class SliderImageAdapter (val context: Context, private var items: ArrayList<SliderImageModel>): androidx.recyclerview.widget.RecyclerView.Adapter<SliderImageAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_slider_image_opsicorp, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items[position]

        val width = Globals.calculatePercentage(83.4,Globals.getWitdhAndHeightLayout(context).first.toDouble())
        val height = Globals.calculatePercentage(60.0,width)

        val lp = LinearLayout.LayoutParams(Globals.toDp(context,(width.toInt())),
                Globals.toDp(context,(height.toInt())))
        holder.itemView.line_item.setLayoutParams(lp)

        Picasso.get()
                .load(data.urlImage)
                .centerCrop()
//                .placeholder(context.resources.getDrawable(R.drawable.background_slider))
                .fit()
                .into(holder.itemView.img_slider)
    }

    fun setData(data: ArrayList<SliderImageModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}