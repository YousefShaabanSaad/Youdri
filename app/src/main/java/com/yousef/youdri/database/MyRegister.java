package com.yousef.youdri.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyRegister {

    private FirebaseAuth auth;
    public MyRegister(){
        auth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getFirebaseUser() {
        return auth.getCurrentUser();
    }
}
