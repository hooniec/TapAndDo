package com.example.tapanddo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Employees extends Fragment implements View.OnClickListener {

    ArrayList<Employee> employees = new ArrayList<>();
    ListView listView;
    FloatingActionButton addBtn;
    static boolean calledAlready = false;

    public Employees(){
        this.employees = getEmployees();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employees, container, false);

        addBtn = view.findViewById(R.id.emp_add);
        addBtn.setOnClickListener(this);

        Log.d("Emp", employees.toString());

        listView = (ListView)view.findViewById(R.id.emp_list);
        EmployeeAdapter empAdapter = new EmployeeAdapter(getContext(), employees);
        listView.setAdapter(empAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee emp = (Employee) empAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("name", emp.getName());
                bundle.putString("position", emp.getPosition());
                bundle.putString("phone", emp.getPhone());
                bundle.putString("email", emp.getEmail());

                EmployeeInfoDialog empInfo = EmployeeInfoDialog.newInstance(new EmployeeInfoDialog.DialogListener() {
                    @Override
                    public void onRemoveRequest(boolean remove) {
                        if(remove){
                            removeEmployee(emp.getId());
                            empAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onUpdateComplete(String name, String position, String phone, String email) {
                        updateEmployee(emp.getId(), name, position, phone, email);
                        empAdapter.notifyDataSetChanged();
                    }
                });

                empInfo.setArguments(bundle);
                empInfo.show(getChildFragmentManager(), "Employee_Info");


            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        EmployeesDialog addEmp = EmployeesDialog.newInstance(new EmployeesDialog.DialogListener() {
            @Override
            public void onInputComplete(String name, String position, String phone, String email) {
                if(name != null && position != null && phone != null && email != null){
                    Long datetime = System.currentTimeMillis();
                    String id = datetime.toString();

                    addEmployee(id, name, position, phone, email);

                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("name", name);
                    hashMap.put("position", position);
                    hashMap.put("phone", phone);
                    hashMap.put("email", email);

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference("Employees");
                    ref.child(id).setValue(hashMap);
                }
            }
        });
        addEmp.show(getChildFragmentManager(), "addDialog");
    }

    public void addEmployee(String id, String name, String position, String phone, String email){
        employees.add(new Employee(id, name, position, phone, email, R.drawable.user));
    }

    public ArrayList<Employee> getEmployees() {
        FirebaseDatabase.getInstance().getReference().child("Employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(employees.isEmpty()){
                    calledAlready = false;
                    if (!calledAlready){
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Employee emp = new Employee();
                            emp.setId(snapshot.child("id").getValue().toString());
                            emp.setName(snapshot.child("name").getValue().toString());
                            emp.setPosition(snapshot.child("position").getValue().toString());
                            emp.setPhone(snapshot.child("phone").getValue().toString());
                            emp.setEmail(snapshot.child("email").getValue().toString());
                            //Log.d("Load Employees", emp.getId() + "" + emp.getName() + "" + emp.getPosition() + "" + emp.getPhone() + "" + emp.getEmail());
                            employees.add(new Employee(emp.getId(), emp.getName(), emp.getPosition(), emp.getPhone(), emp.getEmail(), R.drawable.user));
                        }
                        calledAlready = true;
                    }
                }
            } // Load existing employees

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return employees;
    }

    public void removeEmployee(String id){
        for(int i = 0; i < employees.size(); i++){
            if(id.equalsIgnoreCase(employees.get(i).getId())){
            employees.remove(i);
            }
        }
        FirebaseDatabase.getInstance().getReference().child("Employees").child(id).setValue(null);
    }

    private void updateEmployee(String id, String name, String position, String phone, String email) {
        for(int i = 0; i < employees.size(); i++){
            if(id.equalsIgnoreCase(employees.get(i).getId())){
                employees.set(i, new Employee(id, name, position, phone, email, R.drawable.user));
            }
        }
        DatabaseReference empInfo = FirebaseDatabase.getInstance().getReference("Employees").child(id);
        empInfo.child("name").setValue(name);
        empInfo.child("position").setValue(position);
        empInfo.child("phone").setValue(phone);
        empInfo.child("email").setValue(email);
    }
}