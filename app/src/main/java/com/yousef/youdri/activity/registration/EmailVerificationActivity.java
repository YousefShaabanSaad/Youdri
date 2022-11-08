package com.yousef.youdri.activity.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import com.yousef.mytoast.MyToast;
import com.yousef.youdri.R;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityEmailVerificationBinding;
import com.yousef.youdri.listener.LoginListener;
import com.yousef.youdri.model.Constants;
import com.yousef.youdri.model.User;

public class EmailVerificationActivity extends AppCompatActivity implements LoginListener {

    private ActivityEmailVerificationBinding binding;
    private Repository repository;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
        user = new User();
        init();
        handlingView();
    }

    void init(){
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void handlingView(){
        user.setName(getIntent().getExtras().getString(Constants.NAME));
        user.setEmail(getIntent().getExtras().getString(Constants.EMAIL));
        user.setPassword(getIntent().getExtras().getString(Constants.PASSWORD));
        user.setPhone(Constants.NULL);
        user.setStatus(Constants.ACTIVE);

        StringBuilder text= new StringBuilder();
        text.append(user.getEmail().charAt(0));
        text.append(user.getEmail().charAt(1));
        text.append(user.getEmail().charAt(2));
        for(int i=3; i<user.getEmail().length(); i++){
            if(user.getEmail().charAt(i) == '@') {
                for(int j=i; j<user.getEmail().length(); j++)
                    text.append(user.getEmail().charAt(j));
                binding.email.setText(text.toString());
                return;
            }
            else
                text.append("*");
        }

        binding.verify.setOnClickListener(v -> {
            if(repository.isDisconnect())
                repository.showDisconnectDialog();
            else if(repository.getFirebaseUser().isEmailVerified()) {
                repository.signInWithEmail(user.getEmail(), user.getPassword(), this);
            }
            else
                MyToast.setLongToast(this, getString(R.string.emailOpen), MyToast.FAIL);
        });
    }

    @Override
    public void onSuccess(String text) {
        if(text.equals(Constants.DONE)){
           repository.setIntent(LoginActivity.class);
            finishAffinity();
        }
//        else
//            repository.saveUser(user, this);
    }

    @Override
    public void onFailureEmail(String error) {
        MyToast.setLongToast(this, error, MyToast.FAIL);
    }

    @Override
    public void onFailurePhone(String error) {

    }
}