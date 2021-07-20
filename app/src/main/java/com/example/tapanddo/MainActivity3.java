package com.example.tapanddo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextInputLayout txtUsername, txtEmail, txtPassword, txtPassword2;
    Toolbar toolbar;
    Button btn;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        toolbar=(Toolbar)findViewById(R.id.app_bar); // reference app_bar
        setSupportActionBar(toolbar); // set action bar as the toolbar
        getSupportActionBar().setTitle("Tap N Do - Sign Up"); // set title on action bar

        ab = getSupportActionBar(); // instance of the ActionBar
        ab.setDisplayHomeAsUpEnabled(true); // create back button

        mAuth = FirebaseAuth.getInstance();

        txtUsername = findViewById(R.id.signup_username);
        txtEmail = findViewById(R.id.signup_email);
        txtPassword = findViewById(R.id.signup_pw);
        txtPassword2 = findViewById(R.id.signup_pw2);
        btn = findViewById(R.id.signup_btn); // reference sign up button

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User u = new User();
                u.setEmail(txtEmail.getEditText().getText().toString().trim());
                u.setPassword(txtPassword.getEditText().getText().toString().trim());
                u.setPassword2(txtPassword2.getEditText().getText().toString().trim());
                u.setUsername(txtUsername.getEditText().getText().toString().trim());

                if(!u.getEmail().matches(emailPattern)){
                    Toast.makeText(MainActivity3.this, "Invalid email adress", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(u.getPassword()) || u.getPassword().length() < 6 || TextUtils.isEmpty(u.getPassword2())){
                    Toast.makeText(MainActivity3.this, "Password should be longer than 6", Toast.LENGTH_SHORT).show();
                } else {
                    if(u.getPassword().equals(u.getPassword2())){
                        final ProgressDialog mDialog = new ProgressDialog(MainActivity3.this);
                        mDialog.setMessage("Being processed...");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDialog.show();

                        mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword()).addOnCompleteListener(MainActivity3.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    mDialog.dismiss();

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String email = user.getEmail();
                                    String uid = user.getUid();
                                    String name = u.getUsername();

                                    HashMap<Object,String> hashMap = new HashMap<>();
                                    hashMap.put("uid", uid);
                                    hashMap.put("email", email);
                                    hashMap.put("name", name);

                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = db.getReference("Users");
                                    ref.child(uid).setValue(hashMap);

                                    Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(MainActivity3.this, "Congrats Sign up", Toast.LENGTH_SHORT).show();
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(MainActivity3.this, "Account already exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(MainActivity3.this,"Please make sure your passwords match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
}