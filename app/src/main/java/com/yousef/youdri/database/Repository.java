package com.yousef.youdri.database;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

public class Repository {

    private MyRegister register;

    public Repository(Context context){
        register = new MyRegister();
    }

    public FirebaseUser getFirebaseUser(){
        return register.getFirebaseUser();
    }
}
