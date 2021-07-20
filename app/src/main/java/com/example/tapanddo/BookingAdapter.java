package com.example.tapanddo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class BookingAdapter extends BaseExpandableListAdapter {

    private List<String> listBookingTime;
    private HashMap<String, List<Booking>> listBookingDetail;
    private Context context;

    public BookingAdapter(Context context, List<String> bookingTimes, HashMap<String, List<Booking>> bookingDetails){
        this.context = context;
        listBookingTime = bookingTimes;
        listBookingDetail = bookingDetails;
    }

    @Override
    public int getGroupCount() {
        return listBookingTime.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listBookingDetail.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listBookingTime.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listBookingDetail.get(getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.viewlist_booking_time, null);
        }

        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvCount = (TextView) convertView.findViewById(R.id.tvCount);

        tvTime.setText((String) getGroup(groupPosition));
        tvCount.setText(String.valueOf(getChildrenCount(groupPosition)) + " / 10");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.viewlist_booking, null);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvContact = (TextView) convertView.findViewById(R.id.tvContact);

        Booking booking = (Booking) getChild(groupPosition, childPosition);
        tvName.setText(booking.getName());
        tvContact.setText(booking.getContact());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
