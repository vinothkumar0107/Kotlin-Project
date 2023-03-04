package com.example.location.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.location.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity
{
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private EditText editTxt_first_name,editTxt_email,editTxt_password_register,editTxt_mobile,etage;
    private Button btn_submit;
    TextView signIn;
    private UserData userPojo;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTxt_first_name=findViewById(R.id.et_full_name);
        editTxt_email=findViewById(R.id.et_email);
        editTxt_password_register=findViewById(R.id.et_password);
        editTxt_mobile=findViewById(R.id.et_mobile_number);
       // etage = findViewById(R.id.et_age);
        signIn = findViewById(R.id.tv_sign);
        btn_submit=findViewById(R.id.btn_sign_in);

        auth=FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = editTxt_first_name.getText().toString().trim();
                String email=editTxt_email.getText().toString().trim();
                String password=editTxt_password_register.getText().toString().trim();
                String mobile_no=editTxt_mobile.getText().toString().trim();
                String age = etage.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    editTxt_first_name.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    editTxt_email.setError("Email is required");
                    return;
                }
                if(!email.matches(emailPattern)){
                    editTxt_email.setError("Email is invalid");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    editTxt_password_register.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    editTxt_password_register.setError("Password must be 6 character");
                    return;
                }
                userPojo=new UserData(name,email,password,mobile_no,age);
                registerUser(email,password);
                emptyInputEditText();

            }
        });
    }
    private void registerUser(String email,String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    registerData(user);
                    Toast.makeText(getApplicationContext(), "Register is successful Data stored", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                else {
                    String message = "Something went wrong";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void emptyInputEditText(){
        editTxt_first_name.setText(null);
        editTxt_email.setText(null);
        editTxt_password_register.setText(null);
        editTxt_mobile.setText(null);
        etage.setText(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        registerData(currentUser);
    }
    private void registerData(FirebaseUser currentUser) {

        if (userPojo != null) {

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

            String uid = currentUser.getUid();
            DatabaseReference userRef = databaseReference.child("vinoth list");
            userRef.child(uid).setValue(userPojo);
        }
    }

}