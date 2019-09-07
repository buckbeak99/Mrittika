package com.example.softwareproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth  firebaseAuth;

    private Button signInButton;
    private EditText editEmail;
    private EditText enterPassword;
    private TextView notReg;

    private ProgressBar progBar;
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //go to profile
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        progBar = new ProgressBar(this);

        progDialog = new ProgressDialog(this);

        signInButton = (Button) findViewById(R.id.signInButton);
        editEmail = (EditText) findViewById(R.id.editEmail);
        enterPassword = (EditText) findViewById(R.id.enterPassword);
        notReg = (TextView) findViewById(R.id.notReg);



        signInButton.setOnClickListener(this);
        notReg.setOnClickListener(this);

    }

    private void userLogin(){
        String email = editEmail.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();

            return;
        }
        progDialog.setMessage("Signing In...");
        progDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == signInButton){
            userLogin();
        }
        if(view == notReg){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
