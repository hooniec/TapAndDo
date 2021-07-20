package com.example.tapanddo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class EmployeeInfoDialog extends DialogFragment {

    private EditText name, position, phone, email;
    private DialogListener listener;
    static Boolean removeRequest = false;

    public interface DialogListener {
        void onRemoveRequest(boolean remove);
        void onUpdateComplete(String name, String position, String phone, String email);
    }

    public static EmployeeInfoDialog newInstance(DialogListener listener){
        EmployeeInfoDialog fragment = new EmployeeInfoDialog();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_emp_info_dialog, null);

        Bundle empInfo = getArguments();
        String empName = empInfo.getString("name");
        String empPosition = empInfo.getString("position");
        String empPhone = empInfo.getString("phone");
        String empEmail = empInfo.getString("email");

        name = view.findViewById(R.id.update_emp_name);
        position = view.findViewById(R.id.update_emp_position);
        phone = view.findViewById(R.id.update_emp_phone);
        email = view.findViewById(R.id.update_emp_email);

        name.setText(empName);
        position.setText(empPosition);
        phone.setText(empPhone);
        email.setText(empEmail);

        builder.setView(view).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                listener.onUpdateComplete(name.getText().toString(), position.getText().toString(), phone.getText().toString(), email.getText().toString());
            }
        })
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeRequest = true;
                listener.onRemoveRequest(removeRequest);
            }
        });

        return builder.create();
    }
}
