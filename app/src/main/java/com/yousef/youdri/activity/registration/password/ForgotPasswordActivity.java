package com.yousef.youdri.activity.registration.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import com.yousef.mytoast.MyToast;
import com.yousef.youdri.R;
import com.yousef.youdri.activity.registration.LoginActivity;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityForgotPasswordBinding;
import com.yousef.youdri.listener.EmailListener;
import com.yousef.youdri.listener.PhoneListener;
import com.yousef.youdri.model.Constants;
import com.yousef.youdri.model.User;

/**
 * اللَّهُمَّ صَلِّ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما صَلَّيْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ
 * اللَّهُمَّ بَارِكْ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما بَارَكْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ

 *تم برمجتُه بواسطة : يوسف شعبان
 * +201142747343
 * yousefshaabansaad42@gmail.com
 */

public class ForgotPasswordActivity extends AppCompatActivity implements EmailListener, PhoneListener {

    private ActivityForgotPasswordBinding binding;
    private Repository repository;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
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
        status = Constants.EMAIL;

        binding.emailBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.EMAIL)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg4);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg5);
                status = Constants.EMAIL;
                binding.emailText.setVisibility(View.VISIBLE);
                binding.emailLayout.setVisibility(View.VISIBLE);
                binding.ccp.setVisibility(View.GONE);
                binding.phoneText.setVisibility(View.GONE);
                binding.phoneLayout.setVisibility(View.GONE);
                binding.login.setEnabled(false);
            }
        });

        binding.phoneBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.PHONE)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg5);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg4);
                status = Constants.PHONE;
                binding.emailText.setVisibility(View.GONE);
                binding.emailLayout.setVisibility(View.GONE);
                binding.ccp.setVisibility(View.VISIBLE);
                binding.phoneText.setVisibility(View.VISIBLE);
                binding.phoneLayout.setVisibility(View.VISIBLE);
                binding.login.setEnabled(false);
            }
        });

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()){
                    binding.emailError.setImageResource(com.yousef.mytoast.R.drawable.success);
                    binding.login.setEnabled(true);
                }
                else{
                    binding.emailError.setImageResource(com.yousef.mytoast.R.drawable.error);
                    binding.login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Patterns.PHONE.matcher(binding.phone.getText().toString()).matches()){
                    binding.phoneError.setImageResource(com.yousef.mytoast.R.drawable.success);
                    binding.login.setEnabled(true);
                }
                else{
                    binding.phoneError.setImageResource(com.yousef.mytoast.R.drawable.error);
                    binding.login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.login.setOnClickListener(v -> {
            if(repository.isDisconnect())
                repository.showDisconnectDialog();
            else if(status.equals( Constants.EMAIL)) {
                setVisibility(0.5f, View.VISIBLE);
                repository.resetPasswordWithEmail(binding.email.getText().toString(), this);
            }
            else if(status.equals( Constants.PHONE)){
                setVisibility(0.5f, View.VISIBLE);
                repository.checkPhone(binding.phone.getText().toString(), this);
            }
        });
    }

    void setVisibility(float alpha, int visible){
        binding.progress.setVisibility(visible);
        binding.layout.setAlpha(alpha);
    }


    @Override
    public void sendResetPassword() {
        MyToast.setLongToast(this, getString(R.string.checkEmail) + " " + binding.email.getText().toString(), MyToast.SUCCESS);
        repository.setIntent(LoginActivity.class);
        finishAffinity();
    }

    @Override
    public void fail(String error) {
        setVisibility(1f, View.GONE);
        if(error.equals("There is no user record corresponding to this identifier. The user may have been deleted."))
            MyToast.setLongToast(this, getString(R.string.emailError), MyToast.FAIL);
        else
            MyToast.setLongToast(this, error, MyToast.FAIL);
    }

    @Override
    public void onSuccess(User user) {
        Intent intent = new Intent(getApplicationContext(), PhoneResetActivity.class);
        intent.putExtra(Constants.CODE, binding.ccp.getSelectedCountryNameCode());
        intent.putExtra(Constants.PHONE, binding.phone.getText().toString());
        intent.putExtra(Constants.EMAIL, user.getEmail());
        intent.putExtra(Constants.PASSWORD, user.getPassword());
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String error) {
        setVisibility(1f, View.GONE);
        if(error.equals(Constants.NULL))
            MyToast.setLongToast(this, getString(R.string.phoneError), MyToast.FAIL);
        else
            MyToast.setLongToast(this, error, MyToast.FAIL);
    }
}