package com.example.tapanddo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class Bookings extends Fragment implements View.OnClickListener {

    CalendarView calendarView;
    TextView date;
    Button showBtn;
    FloatingActionButton addBtn;
    int selectedDay, selectedMonth, selectedYear;


    public Bookings(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        date=(TextView)view.findViewById(R.id.booking_date);
        calendarView=(CalendarView)view.findViewById(R.id.booking_calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                selectedDay = dayOfMonth;
                selectedMonth = month;
                selectedYear = year;
                date.setText(selectedDay + " - " + selectedMonth + " - " + selectedYear);
            }
        });

        showBtn = view.findViewById(R.id.show_bottom_sheet_btn);
        showBtn.setOnClickListener(this);
        addBtn = view.findViewById(R.id.add_booking_btn);
        addBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("day", selectedDay);
        bundle.putInt("month", selectedMonth);
        bundle.putInt("year", selectedYear);
        bundle.putString("defaultDate", date.getText().toString());

        if(v.getId() == R.id.show_bottom_sheet_btn){
            BottomSheetDialogFragment bottomSheetDialog = new BookingsBottomSheetDialog();
            bottomSheetDialog.setArguments(bundle);
            bottomSheetDialog.show(getChildFragmentManager(), "Bottom Sheet");
        } else if(v.getId() == R.id.add_booking_btn){
            BookingDialog addBooking = new BookingDialog();
            addBooking.setArguments(bundle);
            addBooking.show(getChildFragmentManager(),"Add Booking");
        }
    }
}