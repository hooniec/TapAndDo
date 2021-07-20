package com.example.tapanddo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter {

    private Context context;
    private List list;

    class ViewHolder {
        public TextView tv_name;
        public TextView tv_position;
        public ImageView iv_profile;
    }

    public EmployeeAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.viewlist_employee, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.emp_name);
        viewHolder.tv_position = (TextView) convertView.findViewById(R.id.emp_position);
        viewHolder.iv_profile = (ImageView) convertView.findViewById(R.id.imageView);

        final Employee employee = (Employee) list.get(position);
        viewHolder.tv_name.setText(employee.getName());
        viewHolder.tv_position.setText(employee.getPosition());
        viewHolder.iv_profile.setImageResource(employee.getProfile());

        return convertView;
    }
}
