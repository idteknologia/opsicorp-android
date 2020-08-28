package com.opsigo.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.opsigo.library.model.ColorData;
import com.opsigo.library.model.DayData;
import com.opsigo.library.R;
import com.opsigo.library.utils.CommonEx;
import com.opsigo.library.utils.InternalEx;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private String mStartDay = "";
    private String mEndDay = "";
    private String mNowFullDay = null;
    private boolean isStart = false;
    private boolean isEnd = false;
    private ArrayList<DayData> mList = new ArrayList<>();
    private ArrayList<String> mListHolidayDate = new ArrayList<>();
    private ArrayList<String> mPrize = new ArrayList<>();
    private ColorData mColorData;
    private boolean mPreventPreviousDate = true;


    public CalendarAdapter(Context mContext, ColorData colorData, boolean preventPreviousDate) {
        this.mColorData = colorData;
        this.mPreventPreviousDate = preventPreviousDate;
        this.mInflater = LayoutInflater.from(mContext);

        Calendar mCalendar = Calendar.getInstance();
        mNowFullDay = String.valueOf(mCalendar.get(Calendar.YEAR));
        mNowFullDay += InternalEx.assignPad10(mCalendar.get(Calendar.MONTH) + 1);
        mNowFullDay += InternalEx.assignPad10(mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setmPrize(ArrayList<String> mPrize) {
        this.mPrize = mPrize;
    }

    public void setmListHolidayDate(ArrayList<String> mListHolidayDate) {
        this.mListHolidayDate = mListHolidayDate;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public DayData getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.calendar_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DayData data = mList.get(position);
        String day = data.getDayStr();

        if (mNowFullDay.equals(data.getFullDay())) {
            holder.dayText.setTextColor(mColorData.getTodayTextColor());
            holder.txPrize.setTextColor(mColorData.getTodayTextColor());
        } else if (CommonEx.compareGreater(data.getFullDay(), mNowFullDay)) {
            holder.dayText.setTextColor(mColorData.getDefaultTextColor());
            holder.txPrize.setTextColor(mColorData.getDefaultTextColor());
        } else if (mPreventPreviousDate) {
            holder.dayText.setTextColor(mColorData.getPrevDayTextColor());
            holder.txPrize.setTextColor(mColorData.getPrevDayTextColor());
        } else {
            holder.dayText.setTextColor(mColorData.getDefaultTextColor());
            holder.txPrize.setTextColor(mColorData.getDefaultTextColor());
        }

        holder.dayText.setBackgroundColor(mColorData.getDefaultBgColor());
        if (isStart && InternalEx.compareDayEqual(mStartDay, data.getFullDay())) {
            holder.dayText.setBackgroundColor(mColorData.getStartDayBgColor());
            holder.dayText.setTextColor(mColorData.getStartDayTextColor());
            holder.txPrize.setTextColor(mColorData.getStartDayTextColor());
        } else if (CommonEx.notEmptyString(mStartDay) && isEnd && InternalEx.compareDayEqual(mEndDay, data.getFullDay())) {
            holder.dayText.setBackgroundColor(mColorData.getEndDayBgColor());
            holder.dayText.setTextColor(mColorData.getEndDayTextColor());
            holder.txPrize.setTextColor(mColorData.getEndDayTextColor());
        } else if (InternalEx.compareDayLessEqual(mStartDay, data.getFullDay()) && InternalEx.compareDayGreatEqual(mEndDay, data.getFullDay())) {
            holder.dayText.setBackgroundColor(mColorData.getSelectedDayBgColor());
        }

        if (position%7==0&&data.getMonth()!=0){
            holder.dayText.setTextColor(Color.parseColor("#fff34646"));
        }

        for (int i = 0 ; i<mListHolidayDate.size();i++){
            if (mListHolidayDate.get(i).equals(String.valueOf(data.getDay()))){
                holder.dayText.setTextColor(Color.parseColor("#fff34646"));
            }
        }

        for (int i = 0 ; i<mPrize.size();i++){
            if (mPrize.get(i).equals(String.valueOf(data.getDay()))){
                holder.txPrize.setText("1.0"+mPrize.get(i));
            }
        }

        holder.dayText.setText(day);

        return convertView;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public void notifyDataSetChanged(ArrayList<DayData> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setStartDay(String startDay) {
        mStartDay = startDay;
    }

    public void setEndDay(String endDay) {
        mEndDay = endDay;
    }

    private class ViewHolder {
        TextView dayText;
        TextView txPrize;

        ViewHolder(View itemView) {
            dayText = itemView.findViewById(R.id.txtDay);
            txPrize = itemView.findViewById(R.id.tx_prize);
        }
    }
}