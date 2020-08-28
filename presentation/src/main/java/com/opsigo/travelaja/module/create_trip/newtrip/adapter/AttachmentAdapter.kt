package com.opsigo.travelaja.module.create_trip.newtrip.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import kotlinx.android.synthetic.main.attach_adapter.view.*

class AttachmentAdapter (val context: Context, private var items: ArrayList<UploadModel>): RecyclerView.Adapter<AttachmentAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.attach_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.tx_name_document.text = data.pathLocalImage

        if (data.statusUploaded=="load"){
            holder.itemView.image_delet.visibility = View.GONE
            holder.itemView.progressBar.visibility = View.VISIBLE
            holder.itemView.tv_failed.visibility   = View.GONE
        }
        else if(data.statusUploaded=="success"){
            holder.itemView.image_delet.visibility = View.VISIBLE
            holder.itemView.progressBar.visibility = View.GONE
            holder.itemView.tv_failed.visibility   = View.GONE
        }
        else if(data.statusUploaded=="fromserver"){
            holder.itemView.image_delet.visibility = View.GONE
            holder.itemView.progressBar.visibility = View.GONE
            holder.itemView.tv_failed.visibility   = View.GONE
        }
        else {
            holder.itemView.image_delet.visibility = View.GONE
            holder.itemView.progressBar.visibility = View.GONE
            holder.itemView.tv_failed.visibility   = View.VISIBLE
        }

        holder.itemView.tv_failed.setOnClickListener {
            onclick.onClick(holder.itemView.tv_failed.id,position)
        }

        holder.itemView.image_delet.setOnClickListener {
            onclick.onClick(holder.itemView.image_delet.id,position)
        }

        holder.itemView.layoutFile.setOnClickListener {
            onclick.onClick(-10,position)
        }



    }

    fun setData(data: ArrayList<UploadModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }


}