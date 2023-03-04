package com.example.location.firebase;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.location.MyService;
import com.example.location.R;
import com.example.location.firebase.fragment.AccountFragment;
import com.example.location.firebase.fragment.ContactFragment;
import com.example.location.firebase.fragment.HomeFragment;
import com.example.location.firebase.fragment.MessageFragment;
import com.example.location.kotlin.KotlinDashboardActivity;
import com.example.location.kotlin.recycler_view.FavoriteFragment;
import com.example.location.kotlin.recycler_view.KotlinAccountFragment;
import com.example.location.map.MyFirebaseMessagingService;
import com.example.location.map.MyWorker;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

public class BottomActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle ;
    FirebaseMessaging firebaseMessaging;
    SharedPreferences sharedPreferences;
    FirebaseAuth firebaseAuth;
    MySharedPerference mySharedPerference;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        startServiceViaWorker();



        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("FIREBASSSE","FIREBASSSE "+ token);
                      //  Toast.makeText(BottomActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


        mySharedPerference = new MySharedPerference(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.side_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(navigationView);
        drawerLayout = findViewById(R.id.main_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    public void onStartServiceClick(View v) {
        startService();
    }

    public void onStopServiceClick(View v) {
        stopService();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy called");
        stopService();
        super.onDestroy();
    }

    public void startService() {
        Log.d(TAG, "startService called");
        if (!MyService.isServiceRunning) {
            Intent serviceIntent = new Intent(this, MyService.class);
            ContextCompat.startForegroundService(this, serviceIntent);
        }
    }

    public void stopService() {
        Log.d(TAG, "stopService called");
        if (MyService.isServiceRunning) {
            Intent serviceIntent = new Intent(this, MyFirebaseMessagingService.class);
            stopService(serviceIntent);
        }
    }

    public void startServiceViaWorker() {
        Log.d(TAG, "startServiceViaWorker called");
        String UNIQUE_WORK_NAME = "StartMyServiceViaWorker";
        WorkManager workManager = WorkManager.getInstance(this);

        // As per Documentation: The minimum repeat interval that can be defined is 15 minutes
        // (same as the JobScheduler API), but in practice 15 doesn't work. Using 16 here
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(
                        MyWorker.class,
                        16,
                        TimeUnit.MINUTES)
                        .build();

        // to schedule a unique work, no matter how many times app is opened i.e. startServiceViaWorker gets called
        // do check for AutoStart permission
        workManager.enqueueUniquePeriodicWork(UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, request);

    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);

                        return true;

                    }

                });

    }
    public void selectDrawerItem(MenuItem menuItem) {

        Fragment fragment = null;
        boolean isClick = true;
        switch(menuItem.getItemId()) {

            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.contact:
                fragment = new ContactFragment();
                break;
            case R.id.account:
                fragment = new AccountFragment();
                break;
            case R.id.message:
                fragment = new MessageFragment();
                break;

            case R.id.logout:
                isClick = false;
                AlertDialog.Builder alert = new AlertDialog.Builder(BottomActivity.this);
                alert.setMessage("Are you sure want to Logout?")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener()
            {
                            public void onClick(DialogInterface dialog, int which) {
                                mySharedPerference.clearData();
                                googleSignInClient.signOut();
                                disconnectFromFacebook();
                                signout();
                            }
                        }).setNegativeButton("Cancel", null);

                AlertDialog alert1 = alert.create();

                alert1.show();
                Button buttonPositive = alert1.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.orange));
                Button buttonNegative = alert1.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.black));
                alert1.show();
                break;

        }
        try {
            setTitle(fragment.getTag());

        } catch (Exception e) {
            e.printStackTrace();

        }
        if(isClick!=false){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_screen, fragment).commit();
        }
       isClick=true;
        //menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();

    }


    private  void signout(){
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: "+task);
                Intent intent = new Intent(BottomActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return;
            // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse)
            {
                LoginManager.getInstance().logOut();
            }
        })
                .executeAsync();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {


        switch (item.getItemId()){
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.contact:
                fragment = new ContactFragment();
                break;
            case R.id.favorite:
                fragment = new FavoriteFragment();
                break;
            case R.id.message:
                fragment = new MessageFragment();
                break;
            case R.id.account:
                fragment = new KotlinAccountFragment();
                break;

        }
        setTitle(item.getTitle());
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;

    }
    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_screen, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
/*
public class HelloWorld
{
    private static String searchText = "vinoth Vino vinoth Vinoth";
    private static String targetWord = "vinoth";
    public static void main(String[] args)
    {
        String[] words = searchText.replaceAll("\\p{Punct}", "").split(" ");
        int wordCount = 0;
        for (int i = 0; i < words.length; i++)
            if (words[i].equals(targetWord))
                wordCount++;
        System.out.println(wordCount);
    }
}*/
