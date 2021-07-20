package com.example.tapanddo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SalesDialog extends DialogFragment {

    private TextView date;
    private EditText cash, card, transfer, total;
    int selectedDay, selectedMonth, selectedYear;
    String selectedDate;
    Button getTotalBtn;
    private DialogListener listener;

    public interface DialogListener {
        void onUpdateComplete(String total);
    }

    public static SalesDialog newInstance(DialogListener listener){
        SalesDialog fragment = new SalesDialog();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sales_dialog, null);

        Bundle addSales = getArguments();
        selectedDay = addSales.getInt("day");
        selectedMonth = addSales.getInt("month");
        selectedYear = addSales.getInt("year");
        selectedDate = addSales.getString("defaultDate");

        date = view.findViewById(R.id.add_sales_date);
        cash = view.findViewById(R.id.add_sales_cash);
        card = view.findViewById(R.id.add_sales_card);
        transfer = view.findViewById(R.id.add_sales_transfer);
        total = view.findViewById(R.id.add_sales_total);
        getTotalBtn = view.findViewById(R.id.add_sales_cal_btn);

        getTotalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cash.getText().toString().isEmpty()){
                    cash.setText("0");
                }
                if(card.getText().toString().isEmpty()){
                    card.setText("0");
                }
                if (transfer.getText().toString().isEmpty()){
                    transfer.setText("0");
                }

                double cashAmount = Double.parseDouble(cash.getText().toString());
                double cardAmount = Double.parseDouble(card.getText().toString());
                double transferAmount = Double.parseDouble(transfer.getText().toString());
                int totalAmount = calculateTotalAmount(cashAmount, cardAmount, transferAmount);

                total.setText(String.valueOf(totalAmount));
            }
        });

        if(selectedDay == 0 || selectedDate.equalsIgnoreCase("Select Date")){
            date.setText("Select a date first");
        } else {
            date.setText(selectedDay + " - " + selectedMonth + " - " + selectedYear);
        }

        builder.setView(view).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String month = String.valueOf(selectedMonth)+String.valueOf(selectedYear);
                String id = selectedDate;

                if(!id.equals("Select Date")){
                    listener.onUpdateComplete(total.getText().toString());

                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("cash", cash.getText().toString());
                    hashMap.put("card", card.getText().toString());
                    hashMap.put("transfer", transfer.getText().toString());
                    hashMap.put("total", total.getText().toString());

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference("Sales");
                    ref.child(month).child(id).setValue(hashMap);
                }

            }
        })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }

    public int calculateTotalAmount(double cash, double card, double transfer){
        int totalAmount = (int) (cash + card + transfer);
        return totalAmount;
    }
}
