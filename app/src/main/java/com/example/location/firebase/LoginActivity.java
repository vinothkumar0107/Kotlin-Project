package com.example.location.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.location.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity  {
    private EditText email,password;
    private Button login;
    Boolean value = true;
    TextView tv_sign_up;
    CardView googleLogin;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MySharedPerference mySharedPerference;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CallbackManager callbackManager ;
    CardView facebookLogin;
    FirebaseUser currentUser;
    LoginManager loginManager;
    UserData userPojo = new UserData();

    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1;
    private AppEventsLogger logger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.btn_login);
        googleLogin = findViewById(R.id.cd_google);
        facebookLogin = findViewById(R.id.facebook);
        email=findViewById(R.id.et_email_login);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        password=findViewById(R.id.et_password_login);


        auth = FirebaseAuth.getInstance();

        mySharedPerference = new MySharedPerference(this);

        FacebookSdk.sdkInitialize(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                facebookLogin();
                loginManager.logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("email", "public_profile","user_birthday"));
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });


        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAccount();
            }
        });

    }

    public void facebookLogin()
    {
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
                            @Override
                            public void onSuccess(LoginResult loginResult)
                            {
                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                                {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response)
                                            {
                                                if (object != null) {
                                                    try {
                                                        mySharedPerference.putBoolean(value);
                                                        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                                                        View dialog_layout = inflater.inflate(R.layout.custom_dialog,null);

                                                        EditText dialogMobile = dialog_layout.findViewById(R.id.et_dialog_mobile);
                                                        EditText dialogAge = dialog_layout.findViewById(R.id.et_dialog_age);
                                                        EditText dialogPassword = dialog_layout.findViewById(R.id.et_dialog_password);
                                                        String name = object.getString("name");
                                                        String email = object.getString("email");
                                                        String fbUserID = object.getString("id");
                                                        String mobile = dialogMobile.getText().toString().trim();
                                                        String age = dialogAge.getText().toString().trim();
                                                        String password = dialogPassword.getText().toString().trim();
                                                        Log.d("FacebookName", "FACEBOOKNAME--------------->: "+name);
                                                        Log.d("FacebookEmail", "FACEBOOK EMAIl--------------->: "+email);

                                                        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
                                                        View view = inflater.inflate(R.layout.custom_dialog,null);
                                                        firebaseDatabase = FirebaseDatabase.getInstance();
                                                        databaseReference = firebaseDatabase.getReference();

                                                        DatabaseReference userRef = databaseReference.child("vinoth list");

                                                        userRef.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot)
                                                            {
                                                                UserData userData = snapshot.getValue(UserData.class);
                                                                boolean isAlready = false;

                                                                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                                                {
                                                                    dataSnapshot.child("email").getValue();
                                                                    //dataSnapshot.child("password").getValue();
                                                                    if(email.equals(dataSnapshot.child("email").getValue())){
                                                                        //    if(googleSignInAccount.getEmail().equals(dataSnapshot.child("password").getValue()))
                                                                        isAlready=true;
                                                                    }
                                                                }


                                                                if(isAlready){
                                                                    startActivity(new Intent(LoginActivity.this,BottomActivity.class));

                                                                } else
                                                                {
                                                                    faceBookRegisterForm(name,email);
                                                                }


                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });

                                                    }
                                                    catch (JSONException | NullPointerException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id, name, email");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel()
                            {
                                Log.v("LoginScreen", "---onCancel");
                            }

                            @Override
                            public void onError(FacebookException error)
                            {
                                // here write code when get error
                                Log.v("LoginScreen", "----onError: "+ error.getMessage());
                            }
                        });
    }

    private void faceBookRegisterForm(String name,String eamil)
    {
        AlertDialog.Builder form = new AlertDialog.Builder(this);
        form.setTitle("REGISTER");
        form.setMessage("Register with email");

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.custom_dialog,null);

        EditText dialogMobile = dialog_layout.findViewById(R.id.et_dialog_mobile);
        EditText dialogAge = dialog_layout.findViewById(R.id.et_dialog_age);
        EditText dialogPassword = dialog_layout.findViewById(R.id.et_dialog_password);
        Button dialogButton = dialog_layout.findViewById(R.id.dialog_button);



        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String fb_name = name;
                String fb_email = eamil;
                String mobile = dialogMobile.getText().toString().trim();
                String age = dialogAge.getText().toString().trim();
                String password = dialogPassword.getText().toString().trim();

                if(TextUtils.isEmpty(dialogMobile.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Mobile Number required ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(dialogAge.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Age is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(dialogPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                userPojo = new UserData(fb_name,fb_email,password,mobile,age);
                registerUser(fb_email,password);

            }
        });
        form.setView(dialog_layout);

        AlertDialog build = form.create();
        build.show();
    }


    private void signin()
    {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,1000);
    }


    private void showRegisterForm()
    {
        AlertDialog.Builder form = new AlertDialog.Builder(this);
        form.setTitle("REGISTER");
        form.setMessage("Register with email");

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.custom_dialog,null);

        EditText dialogMobile = dialog_layout.findViewById(R.id.et_dialog_mobile);
        EditText dialogAge = dialog_layout.findViewById(R.id.et_dialog_age);
        EditText dialogPassword = dialog_layout.findViewById(R.id.et_dialog_password);
        Button dialogButton = dialog_layout.findViewById(R.id.dialog_button);

        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplication());

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String google_name = googleSignInAccount.getDisplayName();
                String google_email = googleSignInAccount.getEmail();
                String mobile = dialogMobile.getText().toString().trim();
                String age = dialogAge.getText().toString().trim();
                String password = dialogPassword.getText().toString().trim();
                //FirebaseUser currentUser = auth.getCurrentUser();

                if(TextUtils.isEmpty(dialogMobile.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Mobile Number required ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(dialogAge.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Age is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(dialogPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                userPojo = new UserData(google_name,google_email,password,mobile,age);
                registerUser(google_email,password);

            }
        });
        form.setView(dialog_layout);

        AlertDialog build = form.create();
        build.show();

    }
    private void registerUser(String email,String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    registerData(user);
                    Toast.makeText(getApplicationContext(), "Register is successful Data stored", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, BottomActivity.class));
                    showRegisterForm();
                }
                else {
                    String message = "Email ID already exits Please go to Login";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                }
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1000){
           Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           try {
               if(task.isSuccessful())
               {
                   task.getResult(ApiException.class);
                   mySharedPerference.putBoolean(value);

                   googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);
                   GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplication());

                   LayoutInflater inflater = LayoutInflater.from(this);
                   View dialog_layout = inflater.inflate(R.layout.custom_dialog,null);
                   firebaseDatabase = FirebaseDatabase.getInstance();
                   databaseReference = firebaseDatabase.getReference();


                   DatabaseReference userRef = databaseReference.child("vinoth list");


                   EditText dialogPassword = dialog_layout.findViewById(R.id.et_dialog_password);
                   String google_email = googleSignInAccount.getEmail();
                  // String password = userPojo.getPassword();
                   userRef.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot)
                       {

                           UserData userData = snapshot.getValue(UserData.class);
                           boolean isAlready = false;

                           for (DataSnapshot dataSnapshot: snapshot.getChildren())
                           {
                               dataSnapshot.child("email").getValue();

                               if(google_email.equals(dataSnapshot.child("email").getValue()))
                               {
                                   String emailId,passwordValue;
                                   emailId = google_email;
                                   passwordValue = (String) dataSnapshot.child("password").getValue();

                                   auth.signInWithEmailAndPassword(emailId,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                       @Override
                                       public void onComplete(@NonNull Task<AuthResult> task)
                                       {
                                           if(task.isSuccessful())
                                           {

                                               Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                                               mySharedPerference.storeData(emailId,passwordValue);
                                               mySharedPerference.putBoolean(value);

                                               startActivity(new Intent(LoginActivity.this, BottomActivity.class));
                                           }
                                           else
                                           {
                                               Toast.makeText(getApplicationContext(), "Please enter valid email id and password", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   });

                                   isAlready=true;
                               }
                           }

                           if(isAlready){
                               {
                                   startActivity(new Intent(LoginActivity.this,BottomActivity.class));
                               }

                           } else
                           {
                               showRegisterForm();
                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

               }

           } catch (ApiException e)
           {
               Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
           }
       }

    }

    private void loginAccount(){
        String emailId,passwordValue;
        emailId=email.getText().toString().trim();
        passwordValue=password.getText().toString().trim();

        if(TextUtils.isEmpty(emailId)){
            Toast.makeText(getApplicationContext(), "Please enter email id", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(passwordValue)){
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return;

        }
        auth.signInWithEmailAndPassword(emailId,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                    mySharedPerference.storeData(emailId,passwordValue);
                    mySharedPerference.putBoolean(value);

                    startActivity(new Intent(LoginActivity.this, BottomActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid email id and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}