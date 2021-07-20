package com.example.tapanddo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BookingDialog extends DialogFragment {

    Spinner spinner;
    TextView date, time;
    EditText name, contact;
    int selectedDay, selectedMonth, selectedYear;
    String selectedDate, selectedTime;
    private DialogListener listener;

    public interface DialogListener {
        void onUpdateComplete(String total);
    }

    public static BookingDialog newInstance(BookingDialog.DialogListener listener){
        BookingDialog fragment = new BookingDialog();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_bookings_dialog, null);

        Bundle addSales = getArguments();
        selectedDay = addSales.getInt("day");
        selectedMonth = addSales.getInt("month");
        selectedYear = addSales.getInt("year");
        selectedDate = addSales.getString("defaultDate");

        date = view.findViewById(R.id.add_booking_date);
        time = view.findViewById(R.id.add_booking_time_text);
        name = view.findViewById(R.id.add_booking_name);
        contact = view.findViewById(R.id.add_booking_contact);
        spinner = view.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(selectedDay == 0 || selectedDate.equalsIgnoreCase("Select Date")){
            date.setText("Select a date first");
        } else {
            date.setText(selectedDay + " - " + selectedMonth + " - " + selectedYear);
        }

        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!selectedDate.equals("Select Date")){
                    Long datetime = System.currentTimeMillis();
                    String id = datetime.toString();

                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("bookingNo", id);
                    hashMap.put("name", name.getText().toString());
                    hashMap.put("contact", contact.getText().toString());
                    hashMap.put("time", selectedTime);

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference("Bookings");
                    ref.child(selectedDate).child(selectedTime).child(id).setValue(hashMap);
                }
            }
        })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }
}
