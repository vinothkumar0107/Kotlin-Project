package com.example.location.firebase.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.location.R;
import com.example.location.firebase.MySharedPerference;
import com.example.location.firebase.UserData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountFragment extends Fragment
{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    TextView name,email,mobile,age;
    ArrayList<UserData> list = new ArrayList<>();
    MySharedPerference mySharedPerference;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    private String googleSignIn = "";
    private String firebaseSignIn = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        name = view.findViewById(R.id.tv_name);
        email = view.findViewById(R.id.tv_email);
        mobile = view.findViewById(R.id.tv_mobile);
        age = view.findViewById(R.id.tv_age);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(),googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext());


        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(databaseReference!=null)
        {
            firebaseDatabase = FirebaseDatabase.getInstance();
            if(currentUser != null){
                String uid = currentUser.getUid();
                databaseReference.child("vinoth list").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            UserData userData = snapshot.getValue(UserData.class);
                            list.add(userData);
                            name.setText("Name : "+userData.getFullName());
                            email.setText("Email : "+userData.getEmail());
                            mobile.setText("Mobile : "+userData.getMobile());
                            age.setText("Age : "+userData.getAge());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
       return view;


    }
}