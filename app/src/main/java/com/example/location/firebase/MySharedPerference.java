package com.example.location.firebase;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPerference
{
    SharedPreferences sharedPreferences;
   SharedPreferences.Editor editor;

   public static String login="logIn";
    public String googleType = "google";

    public MySharedPerference(Context context){
        sharedPreferences = context.getSharedPreferences("user_data",0);
        editor=sharedPreferences.edit();
    }
    public int storeData(String email, String password){
        editor.putString("userEmail",email);
        editor.putString("userPassword",password);
        return 0;
    }

    public int google_sign_in(String google_email,String google_name){

        editor.putString("google_email",google_email);
        editor.putString("google_name",google_name);
        return 0;
    }

   public void putBoolean(boolean value){
        editor.putBoolean(login,value).apply();
   }
   public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
   }

   public void clearData(){
       editor.clear().apply();
      // editor.apply();
   }
}
