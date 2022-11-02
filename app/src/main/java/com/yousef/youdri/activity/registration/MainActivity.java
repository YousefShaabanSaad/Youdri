package com.yousef.youdri.activity.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yousef.youdri.R;
import com.yousef.youdri.database.Repository;

public class MainActivity extends AppCompatActivity {

    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new Repository(this);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                    init();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void init(){
        Intent intent = new Intent();
        if(repository.getFirebaseUser() == null){
            intent.setClass(this, LoginActivity.class);
        }
//        else
//            intent.setClass(this, HomeActivity.class);

        startActivity(intent);
        finish();
    }
}