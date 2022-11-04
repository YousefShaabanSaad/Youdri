package com.yousef.youdri.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.yousef.youdri.R;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.listener.MainListener;
import com.yousef.youdri.model.Constants;
import com.yousef.youdri.model.User;

/**
 * اللَّهُمَّ صَلِّ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما صَلَّيْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ
 * اللَّهُمَّ بَارِكْ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما بَارَكْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ

 *تم برمجتُه بواسطة : يوسف شعبان
 * +201142747343
 * yousefshaabansaad42@gmail.com
 */

public class MainActivity extends AppCompatActivity implements MainListener {

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
        if(repository.getFirebaseUser() == null)
            repository.setIntent(LoginActivity.class);
        else
            repository.getUser(MainActivity.this);
        finish();
    }

    @Override
    public void getUser(User user) {
        if (user.getStatus() != null ) {
            if (user.getStatus().equals(Constants.BLOCK)) {
                repository.setIntent(BlockActivity.class);
                finish();
            } else if (user.getStatus().equals(Constants.ACTIVE)) {
                //repository.setIntent(getApplicationContext(), HomeActivity.class);
                finish();
            }
        }
        else
            repository.signOut();
    }

    @Override
    public void fail(String error) {
        repository.signOut();
        repository.setIntent(LoginActivity.class);
        finish();
    }
}