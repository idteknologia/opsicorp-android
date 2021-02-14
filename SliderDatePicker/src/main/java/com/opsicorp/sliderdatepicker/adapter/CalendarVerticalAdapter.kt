package com.khoiron.sliderdatepicker.adapter

import android.view.View
import java.util.ArrayList
import android.view.ViewGroup
import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import com.khoiron.sliderdatepicker.R
import android.support.v7.widget.RecyclerView
import com.khoiron.sliderdatepicker.utils.Constant
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v4.content.res.ResourcesCompat
import com.khoiron.sliderdatepicker.model.CalendarModel
import com.khoiron.contact.module.StickyHeaderInterface
import com.khoiron.sliderdatepicker.utils.Constant.VIEW_TYPE_BODY
import com.khoiron.sliderdatepicker.utils.Constant.VIEW_TYPE_HEADER
import kotlinx.android.synthetic.main.body_calendar_vertical_view.view.*
import kotlinx.android.synthetic.main.header_calendar_vertical_view.view.*

/**
 * Created by khoiron on 04/09/18.
 */

class CalendarVerticalAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),StickyHeaderInterface {

    var dataList = ArrayList<CalendarModel>()
    lateinit var onclick :OnclickListenerRecyclerViewParent

    fun setData(dataList: ArrayList<CalendarModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){

            VIEW_TYPE_HEADER -> HeaderHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_calendar_vertical_view, parent, false))

            else -> BodyHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.body_calendar_vertical_view, parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            VIEW_TYPE_HEADER -> (holder as HeaderHolder).bind(dataList[position],position)
            VIEW_TYPE_BODY   -> (holder as BodyHolder).bind(dataList[position],position)
        }
    }

    open inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        val typeLayout = dataList[position].typeLayout
        return when (typeLayout){
            VIEW_TYPE_HEADER    -> VIEW_TYPE_HEADER
            else                -> VIEW_TYPE_BODY
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class HeaderHolder internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: CalendarModel, position: Int) {
            itemView.tv_title_month.setText(data.dateStr)
            setFontHeader(itemView.tv_day_sun,
                itemView.tv_day_mon,
                itemView.tv_day_thu,
                itemView.tv_day_wed,
                itemView.tv_day_tue,
                itemView.tv_day_fri,
                itemView.tv_day_sat,
                itemView.tv_title_month)
        }
    }

    private fun setFontHeader(
        tvDaySun: TextView,
        tvDayMon: TextView,
        tvDayThu: TextView,
        tvDayWed: TextView,
        tvDayTue: TextView,
        tvDayFri: TextView,
        tvDaySat: TextView,
        tvTitleMonth: TextView
    ) {
        if (Constant.fontDayHeader!=-1){
            try {
                val typefaceTitle = ResourcesCompat.getFont(context, Constant.fontDayHeader)
                tvDaySun.setTypeface(typefaceTitle)
                tvDayMon.setTypeface(typefaceTitle)
                tvDayThu.setTypeface(typefaceTitle)
                tvDayWed.setTypeface(typefaceTitle)
                tvDayTue.setTypeface(typefaceTitle)
                tvDayFri.setTypeface(typefaceTitle)
                tvDaySat.setTypeface(typefaceTitle)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        if (Constant.fontMonthHeader!=-1){
            try {
                val typefaceTitle = ResourcesCompat.getFont(context, Constant.fontMonthHeader)
                tvTitleMonth.setTypeface(typefaceTitle)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


    inner class BodyHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: CalendarModel, positionParent:Int) {
            val dateAdapter by lazy { DateAdapter(context,data.data) }
            val mLayoutManager = GridLayoutManager(context, 7)
            itemView.rv_date.setLayoutManager(mLayoutManager)
            itemView.rv_date.setHasFixedSize(true)
            itemView.rv_date.setAdapter(dateAdapter)
            itemView.rv_date.setNestedScrollingEnabled(false)

            dateAdapter.setOnclickListener(object :OnclickListenerRecyclerView{
                override fun callbackRecyclerView(view: Int, position: Int) {
                    onclick.callbackRecyclerView(Constant.ONCLICK_DATE,positionParent,-1,position)
                }
            })

            val adapter by lazy { HolidayAdapter(context,data.dataHoliday) }
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            itemView.rv_holiday.setItemAnimator(DefaultItemAnimator())
            itemView.rv_holiday.setNestedScrollingEnabled(false)
            itemView.rv_holiday.layoutManager = layoutManager
            itemView.rv_holiday.setHasFixedSize(true)
            itemView.rv_holiday.setAdapter(adapter)
        }
    }

    fun callbackRecyclerView(callback:OnclickListenerRecyclerViewParent){
        onclick = callback
    }


    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        return itemPosition
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return R.layout.header_calendar_vertical_view
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        val tv_title_month = header.findViewById(R.id.tv_title_month) as TextView
        val tv_day_sun     = header.findViewById(R.id.tv_day_sun) as TextView
        val tv_day_mon     = header.findViewById(R.id.tv_day_mon) as TextView
        val tv_day_thu     = header.findViewById(R.id.tv_day_thu) as TextView
        val tv_day_wed     = header.findViewById(R.id.tv_day_wed) as TextView
        val tv_day_tue     = header.findViewById(R.id.tv_day_tue) as TextView
        val tv_day_fri     = header.findViewById(R.id.tv_day_fri) as TextView
        val tv_day_sat     = header.findViewById(R.id.tv_day_sat) as TextView
        setFontHeader(tv_title_month,
            tv_day_sun,
            tv_day_mon,
            tv_day_thu,
            tv_day_wed,
            tv_day_tue,
            tv_day_fri,
            tv_day_sat)
        tv_title_month.setText(dataList[headerPosition].dateStr)

    }

    override fun isHeader(itemPosition: Int): Boolean {
        return dataList.get(itemPosition).typeLayout == VIEW_TYPE_HEADER;
    }

}
