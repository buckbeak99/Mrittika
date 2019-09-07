package com.example.softwareproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth  firebaseAuth;

    private Button registerButton;
    private EditText editEmail;
    private EditText enterPassword;
    private TextView alreadyReg;
    private ProgressBar progBar;
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //go to profile
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        progBar = new ProgressBar(this);

        progDialog = new ProgressDialog(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        editEmail = (EditText) findViewById(R.id.editEmail);
        enterPassword = (EditText) findViewById(R.id.enterPassword);
        alreadyReg = (TextView) findViewById(R.id.alreadyReg);

        registerButton.setOnClickListener(this);
        alreadyReg.setOnClickListener(this);
    }

    private void registerUSer(){

        String email = editEmail.getText().toString().trim();
        String password  = enterPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();

            return;
        }

        progDialog.setMessage("Registering...");
        progDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Couldn't Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {

        if(view == registerButton){
            registerUSer();
        }

        if(view == alreadyReg){
            startActivity(new Intent(this, Login.class));
        }
    }
}
