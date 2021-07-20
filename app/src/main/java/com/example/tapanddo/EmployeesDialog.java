package com.example.tapanddo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class EmployeesDialog extends DialogFragment {

    private EditText name, position, phone, email;
    private DialogListener listener;

    public interface DialogListener {
        void onInputComplete(String name, String position, String phone, String email);
    }

    public static EmployeesDialog newInstance(DialogListener listener){
        EmployeesDialog fragment = new EmployeesDialog();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_employees_dialog, null);

        name = view.findViewById(R.id.add_emp_name);
        position = view.findViewById(R.id.add_emp_position);
        phone = view.findViewById(R.id.add_emp_phone);
        email = view.findViewById(R.id.add_emp_email);

        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                listener.onInputComplete(name.getText().toString(), position.getText().toString(), phone.getText().toString(), email.getText().toString());
            }
        }).setNegativeButton("Cancel", null);

        return builder.create();
    }


}
