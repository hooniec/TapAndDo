package com.example.tapanddo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Sales extends Fragment implements View.OnClickListener {

    CalendarView calendarView;
    TextView date, salesDay, salesMonth;
    int selectedDay, selectedMonth, selectedYear;
    FloatingActionButton addBtn;

    public Sales(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);

        addBtn=(FloatingActionButton)view.findViewById(R.id.sales_add);
        addBtn.setOnClickListener(this);

        date=(TextView)view.findViewById(R.id.sales_date);
        salesDay=(TextView)view.findViewById(R.id.sales_total_value);
        salesMonth=(TextView)view.findViewById(R.id.sales_total_month_value);
        calendarView=(CalendarView)view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                selectedDay = dayOfMonth;
                selectedMonth = month;
                selectedYear = year;
                date.setText(selectedDay + " - " + selectedMonth + " - " + selectedYear);

                getSales(selectedDay, selectedMonth, selectedYear);
            }
        });

        return view;
    }

    public void getSales(int selectedDay, int selectedMonth, int selectedYear) {
        String theDay = String.valueOf(selectedDay);
        String theMonth = String.valueOf(selectedMonth);
        String theYear = String.valueOf(selectedYear);

        FirebaseDatabase.getInstance().getReference().child("Sales").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                salesDay.setText((CharSequence) snapshot.child(theMonth+theYear).child(String.valueOf(theDay + " - " + theMonth + " - " + theYear)).child("total").getValue());
                if(salesDay.getText().toString().isEmpty()){
                    salesDay.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Sales").child(theMonth+theYear).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int monthTotal = 0;
                for(DataSnapshot data: snapshot.getChildren()){
                    String amount = data.child("total").getValue(String.class);
                    int total = Integer.parseInt(amount);
                    monthTotal += total;
                }
                salesMonth.setText(String.valueOf(monthTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("day", selectedDay);
        bundle.putInt("month", selectedMonth);
        bundle.putInt("year", selectedYear);
        bundle.putString("defaultDate", date.getText().toString());

        SalesDialog addSales = SalesDialog.newInstance(new SalesDialog.DialogListener() {
            @Override
            public void onUpdateComplete(String total) {
                if(total.isEmpty()){
                    salesDay.setText("0");
                } else {
                    salesDay.setText(total);
                }
            }
        });

        addSales.setArguments(bundle);
        addSales.show(getChildFragmentManager(), "Add_Sales");
    }
}