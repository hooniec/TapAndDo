package com.example.tapanddo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingsBottomSheetDialog extends BottomSheetDialogFragment {

    BottomSheetListener listener;
    Button hideBtn;
    ExpandableListView expandableListView;
    int selectedDay, selectedMonth, selectedYear;
    String selectedDate;
    TextView date;
    static boolean calledAlready = false;
    List<Booking> listFor1stTime = new ArrayList<>();
    List<Booking> listFor2ndTime = new ArrayList<>();
    List<Booking> listFor3rdTime = new ArrayList<>();
    List<Booking> listFor4thTime = new ArrayList<>();
    List<Booking> listFor5thTime = new ArrayList<>();

    public BookingsBottomSheetDialog(){
    }

    public interface BottomSheetListener{
        void onButtonClicked(String text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), 0);
        dialog.setContentView(R.layout.fragment_bookings_bottom_sheet);

        Bundle loadDate = getArguments();
        selectedDay = loadDate.getInt("day");
        selectedMonth = loadDate.getInt("month");
        selectedYear = loadDate.getInt("year");
        selectedDate = loadDate.getString("defaultDate");

        date = dialog.findViewById(R.id.load_booking_date);

        if(selectedDay == 0 || selectedDate.equalsIgnoreCase("Select Date")){
            date.setText("Select a date first");
        } else {
            date.setText(selectedDay + " - " + selectedMonth + " - " + selectedYear);
        }

        hideBtn = dialog.findViewById(R.id.hide_bottom_sheet_btn);
        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        expandableListView = (ExpandableListView) dialog.findViewById(R.id.expandable_list);

        final List<String> listBookingTime = new ArrayList<>();
        listBookingTime.add("11:30 ~ 13:00");
        listBookingTime.add("13:00 ~ 14:30");
        listBookingTime.add("17:00 ~ 18:30");
        listBookingTime.add("18:30 ~ 20:00");
        listBookingTime.add("20:00 ~ 22:00");

        final HashMap<String, List<Booking>> listBookingDetail = new HashMap<>();
        listBookingDetail.put(listBookingTime.get(0), listFor1stTime);
        listBookingDetail.put(listBookingTime.get(1), listFor2ndTime);
        listBookingDetail.put(listBookingTime.get(2), listFor3rdTime);
        listBookingDetail.put(listBookingTime.get(3), listFor4thTime);
        listBookingDetail.put(listBookingTime.get(4), listFor5thTime);

        final BookingAdapter bookingAdapter = new BookingAdapter(getContext(), listBookingTime, listBookingDetail);
        expandableListView.setAdapter(bookingAdapter);
        getBookingsByTimeSection(bookingAdapter);



        return dialog;
    }

    public void getBookingsByTimeSection(BookingAdapter adapter){
        FirebaseDatabase.getInstance().getReference("Bookings").child(selectedDate).child("11:30 ~ 13:00").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String name = snapshot.child("name").getValue(String.class);
                    String contact = snapshot.child("contact").getValue(String.class);

                    listFor1stTime.add(new Booking(name, contact));

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Bookings").child(selectedDate).child("13:00 ~ 14:30").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String name = snapshot.child("name").getValue(String.class);
                    String contact = snapshot.child("contact").getValue(String.class);

                    listFor2ndTime.add(new Booking(name, contact));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Bookings").child(selectedDate).child("17:00 ~ 18:30").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String name = snapshot.child("name").getValue(String.class);
                    String contact = snapshot.child("contact").getValue(String.class);

                    listFor3rdTime.add(new Booking(name, contact));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Bookings").child(selectedDate).child("18:30 ~ 20:00").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String name = snapshot.child("name").getValue(String.class);
                    String contact = snapshot.child("contact").getValue(String.class);

                    listFor4thTime.add(new Booking(name, contact));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Bookings").child(selectedDate).child("20:00 ~ 22:00").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String name = snapshot.child("name").getValue(String.class);
                    String contact = snapshot.child("contact").getValue(String.class);

                    listFor5thTime.add(new Booking(name, contact));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
