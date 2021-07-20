package com.example.tapanddo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Toolbar toolbar;
    TextInputLayout txtEmail, txtPassword;
    Button login, resetPw, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.app_bar); // reference app_bar
        setSupportActionBar(toolbar); // set action bar as the toolbar
        getSupportActionBar().setTitle("Tap N Do - Login"); // set title on action bar
        
        txtEmail = findViewById(R.id.login_email); // reference email text
        txtPassword = findViewById(R.id.login_pw); // reference password text
        login = findViewById(R.id.login_btn); // reference login button
        resetPw = findViewById(R.id.login_reset_pw); // reference reset password button
        signup = findViewById(R.id.login_sign_up); // reference sign up button

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            finish();
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        } else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final User u = new User();
                    u.setEmail(txtEmail.getEditText().getText().toString().trim());
                    u.setPassword(txtPassword.getEditText().getText().toString().trim());

                    if(TextUtils.isEmpty(u.getEmail()) || TextUtils.isEmpty(u.getPassword())){
                        Toast.makeText(MainActivity.this, "Enter login details", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.signInWithEmailAndPassword(u.getEmail(), u.getPassword()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } // onClick()
            }); // setOnClickListener()
        }

        resetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getEditText().getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter the email to reset password", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "We sent you an email to reset password", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No account search results", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            } // onClick()
        }); // setOnClickListener()
    } // onCreate()

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.add(0,1,1, menuIconWithText(getResources().getDrawable(R.drawable.ic_help), "Help")).setContentDescription("Help");
        menu.add(0,2,2, menuIconWithText(getResources().getDrawable(R.drawable.ic_settings), "Settings")).setContentDescription("Settings");
        return super.onCreateOptionsMenu(menu);
    } // onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Intent help = new Intent(MainActivity.this, Help.class);
                startActivity(help);
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settings);
                Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
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

