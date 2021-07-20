package com.example.tapanddo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Toolbar toolbar;
    Fragment sales;
    Fragment bookings;
    Fragment employees;
    private final static String EMPLOYEES_TAG = "EMPLOYEES_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = (Toolbar)findViewById(R.id.app_bar); // reference app_bar
        setSupportActionBar(toolbar); // set action bar as the toolbar
        getSupportActionBar().setTitle("Tap N Do - Manage Store"); // set title on action bar

        sales = new Sales();
        bookings = new Bookings();
        employees = new Employees();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(user.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity2.this, "Welcome " + task.getResult().getValue(), Toast.LENGTH_LONG).show();
                }
            }
        }); // Generate greeting message

        getSupportFragmentManager().beginTransaction().replace(R.id.container, sales).commit(); // set initial display
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.tab_sales:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, sales).commit();
                                return true;
                            case R.id.tab_bookings:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, bookings).commit();
                                return true;
                            case R.id.tab_Employees:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, employees).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.add(0,1,1, menuIconWithText(getResources().getDrawable(R.drawable.ic_help), "Help")).setContentDescription("Help");
        menu.add(0,2,2, menuIconWithText(getResources().getDrawable(R.drawable.ic_settings), "Settings")).setContentDescription("Settings");
        menu.add(0,3,3, menuIconWithText(getResources().getDrawable(R.drawable.ic_logout), "Sign Out")).setContentDescription("Sign Out");
        return super.onCreateOptionsMenu(menu);
    } // onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Intent help = new Intent(MainActivity2.this, Help.class);
                startActivity(help);
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Intent settings = new Intent(MainActivity2.this, SettingsActivity.class);
                startActivity(settings);
                Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent login = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(login);
                Toast.makeText(getApplicationContext(), "You have safely signed out", Toast.LENGTH_SHORT).show();
                break;

            default:
        } // switch item

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected()

    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
